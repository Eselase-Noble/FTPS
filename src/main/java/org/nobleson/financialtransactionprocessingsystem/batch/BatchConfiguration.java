//package org.nobleson.financialtransactionprocessingsystem.batch;
//
//
//import lombok.RequiredArgsConstructor;
//import org.nobleson.financialtransactionprocessingsystem.models.Transaction;
//import org.nobleson.financialtransactionprocessingsystem.repositories.TransactionRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.listener.JobExecutionListenerSupport;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.Collections;
//
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class BatchConfiguration {
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Bean
//    public ItemReader<Transaction> reader() {
//
//        return new RepositoryItemReaderBuilder<Transaction>()
//                .name("transactionItemReader")
//                .repository(transactionRepository)
//                .methodName("findAll")
//                .sorts(Collections.singletonMap("transactionId", Sort.Direction.ASC))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Transaction, Transaction> processor() {
//        return transaction -> {
//            transaction.setTransactionType(transaction.getTransactionType());
//            return transaction;
//        };
//    }
//
//    @Bean
//    public ItemWriter<Transaction> writer() {
//         return transactionRepository::saveAll;
//    }
//
//    @Bean
//    public Job processTransactionsJob(JobRepository jobRepository, Step step, JobExecutionListener jobExecutionListener) {
//        return new JobBuilder("processTransactionsJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .listener(jobExecutionListener)
//                .start(step)
//                .build();
//
//    }
//
//    @Bean
//    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("Step", jobRepository)
//                .<Transaction, Transaction>chunk(10,transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    public JobExecutionListener listener(){
//        return new JobExecutionListenerSupport(){
//            @Override
//            public void afterJob(JobExecution jobExecution) {
//
//                System.out.println("Job started " + jobExecution.getJobInstance().getJobName());
//            }
//
//            @Override
//            public void beforeJob(JobExecution jobExecution) {
//
//                if (jobExecution.getStatus().isUnsuccessful()){
//                    System.out.println("Job failed: " + jobExecution.getJobInstance().getJobName());
//                }
//                else {
//                    System.out.println("Job completed successfully: " + jobExecution.getJobInstance().getJobName());
//                }
//            }
//
//        };
//    }
//}