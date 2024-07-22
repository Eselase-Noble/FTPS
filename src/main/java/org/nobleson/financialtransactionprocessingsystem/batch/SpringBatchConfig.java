package org.nobleson.financialtransactionprocessingsystem.batch;


import lombok.AllArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Customer;
import org.nobleson.financialtransactionprocessingsystem.partition.ColumnRangePartitioner;
import org.nobleson.financialtransactionprocessingsystem.repositories.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@AllArgsConstructor
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomerRepository customerRepository;
    private final CustomerWriter customerWriter;


    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new ClassPathResource("customers.csv"));
        itemReader.setName("CSV-File-Reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames( "customerId", "address", "city", "email", "otherName", "surname" , "phoneNumber" );

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public CustomerProcessor processor(){
        return new CustomerProcessor();
    }

    @Bean
    public ColumnRangePartitioner partitioner(){
        return new ColumnRangePartitioner();
    }

//    @Bean
//    public RepositoryItemWriter<Customer> writer(){
//
//        RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
//        itemWriter.setRepository(customerRepository);
//        itemWriter.setMethodName("save");
//        return itemWriter;
//    }

    @Bean
    public PartitionHandler partitionHandler(){
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(2);
        partitionHandler.setTaskExecutor(taskExecutor());
        partitionHandler.setStep(slaveStep());
        return partitionHandler;
        }

    @Bean
    public Step slaveStep(){
        return new StepBuilder("slaveStep", jobRepository)
                .<Customer, Customer> chunk(500, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(customerWriter)
                .build();
    }



    @Bean
    public Step masterStep(){
        return new StepBuilder("masterStep", jobRepository)
                .partitioner(slaveStep().getName(), partitioner())
                .partitionHandler(partitionHandler())
                .build();

    }

//    @Bean
//    public Step step1 (){
//        return new StepBuilder("CSV-STEP", jobRepository)
//                .<Customer, Customer> chunk(100, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .taskExecutor(taskExecutor())
//                .build();
//    }

    @Bean
    public Job runJob(){
        return new JobBuilder("INPUT-CUSTOMER-DATA", jobRepository)
                .flow(masterStep())
                .end()
                .build();
    }


    @Bean
    public TaskExecutor taskExecutor(){
//        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
//        taskExecutor.setThreadNamePrefix("taskExecutor");
//        taskExecutor.setConcurrencyLimit(10);
//        return taskExecutor;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(4);
        executor.setCorePoolSize(4);
        executor.setQueueCapacity(4);
        return executor;
    }
}
