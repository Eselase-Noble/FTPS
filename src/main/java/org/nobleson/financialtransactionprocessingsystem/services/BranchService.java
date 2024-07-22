package org.nobleson.financialtransactionprocessingsystem.services;

import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Account;
import org.nobleson.financialtransactionprocessingsystem.models.Branch;
import org.nobleson.financialtransactionprocessingsystem.repositories.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle the services rendered by the Branch entity leveraging on the repository
 * It handles the CRUD operations needed in the application
 * @author Nobleson
 * @version 1.0.0
 * @date 09/07/2024
 *
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BranchService {

    //Instantiating the Branch repository
    private final BranchRepository branchRepository;


    /**
     * This method is used to create a new branch
     * @param branch
     * @return
     */

    public Branch createBranch(Branch branch) {
        long startTime = System.nanoTime();

//        if (branchRepository.existsById(branch.getBranchId()) ) {
//            throw new RuntimeException("Branch already exists");
//        }

        long endTime = System.nanoTime();


        System.out.println(" Duration: " + (endTime - startTime));

        return branchRepository.save(branch);
    }

    /**
     * This method works just the above, but it allows the user to create more users at a go
     * @param branches
     * @return
     */
    public List<Branch> createBranches(List<Branch> branches) {

        //Check if the list is empty and prompt the user
        if (branches.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }

        //iterate through the list and check if some of the branches already exist in the db.
        //Prompt the users when they do exist
        for (Branch branch : branches) {
            if (branchRepository.existsById(branch.getBranchId())) {
                throw new RuntimeException("Branch already exists");
            }
        }

        //save all of them using the saveAll method from the Jpa repository

        return branchRepository.saveAll(branches);
    }


    /**
     * This method is responsible for get all the branch records in the db
     * @return
     */
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();

    }


    /**
     * Get a branch based on the branch id
     * @param branchId
     * @return
     */
    public Branch findBranch(Long branchId) {

        return branchRepository.findById(branchId).
                orElseThrow(()-> new
                        RuntimeException("Branch does not exist with Id: " + branchId));
    }

    /**
     * Method overloading since this method and previous one have the same method name but different arguments
     * this is also used to get a branch based on the branch Name
     * @param branchName
     * @return
     */
    public Branch findBranch(String branchName) {
        return branchRepository.
                findByBranchName(branchName).
                orElseThrow(()-> new
                        RuntimeException("Branch does not exist with name: " + branchName));
    }

//    public Branch findBranch(Object object){
//        return branchRepository.existsBranchByBranchIdOrBranchName(object).orElseThrow(()-> new RuntimeException("Branch does not exist"));
//    }

    /**
     * This method is used to edit or update the existing branch in the db
     * @param branch
     * @return
     */
    public Branch updateBranch(Branch branch) {
        if (!branchRepository.existsById(branch.getBranchId()) || !branchRepository.existsBranchByBranchName(branch.getBranchName())) {
            throw new RuntimeException("Branch does not exist with name: " + branch.getBranchName());
        }
        return branchRepository.save(branch);
    }

    /**
     * This method is similar to the @method updateBranch
     * it is used to update more than one branch
     * @param branches
     * @return
     */
    public List<Branch> updateBranches(List<Branch> branches) {
        if (branches.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }

        for (Branch branch : branches) {
            if (!branchRepository.existsById(branch.getBranchId()) || !branchRepository.existsBranchByBranchName(branch.getBranchName())) {
                throw new RuntimeException("Branch does not exist with name: " + branch.getBranchName());
            }
        }
        return branchRepository.saveAll(branches);
    }


    /**
     * This method is responsible for deleting branch by specifying the branch id
     * @param branchId
     */
    public void deleteBranch(Long branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new RuntimeException("Branch does not exist with Id: " + branchId);
        }
        branchRepository.deleteById(branchId);
    }

    /**
     * This is used to delete a branch based on the branch name
     * @param branchName
     */
    public void deleteBranch(String branchName) {
        if (!branchRepository.existsBranchByBranchName(branchName)){
            throw new RuntimeException("Branch does not exist with name: " + branchName);
        }
        branchRepository.deleteByBranchName(branchName);
    }

    /**
     * This is used to delete list of specified branches
     * @param branches
     */
    public void deleteBranches(List<Branch> branches) {
        if (branches.isEmpty()) {
            throw new RuntimeException("Account list cannot be empty");
        }
        for (Branch branch : branches) {
            if (!branchRepository.existsById(branch.getBranchId())) {
                throw new RuntimeException("Branch does not exist with Id: " + branch.getBranchId());
            }
        }
        branchRepository.deleteAll(branches);
    }

    /**
     * This method deletes all the branches in the db
     */
    public void deleteAllBranches(){
        branchRepository.deleteAll();
    }
}
