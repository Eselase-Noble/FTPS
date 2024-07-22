package org.nobleson.financialtransactionprocessingsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.nobleson.financialtransactionprocessingsystem.models.Branch;
import org.nobleson.financialtransactionprocessingsystem.services.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to handle the controller rendered by the Branch entity leveraging on the repository
 * It handles the APIs generated request needed in the application
 * @author Nobleson
 * @version 1.0.0
 * @date 09/07/2024
 *
 */


@RequiredArgsConstructor
@RestController
@ControllerAdvice
@Controller
@RequestMapping("/FTPS/bank/branch")
public class BranchController {

    private final BranchService branchService;



    @PostMapping("/add-branch")
    public ResponseEntity<String> addBranch(@RequestBody final Branch branch) {

        branchService.createBranch(branch);
        return new ResponseEntity<>("BRANCH SUCCESSFULLY CREATED", HttpStatus.CREATED);
    }

    @PostMapping("/add-more-branches")
    public ResponseEntity<String> addBranches(@RequestBody final List<Branch> branches) {

        branchService.createBranches(branches);
        return new ResponseEntity<>("BRANCHES SUCCESSFULLY CREATED", HttpStatus.CREATED);
    }

    @GetMapping("/all-branches")
    public ResponseEntity<List<Branch>> getBranches() {

        return new ResponseEntity<>(branchService.getAllBranches(), HttpStatus.OK);
    }

    @GetMapping("/find-branch/{branchId}")
    public ResponseEntity<Branch> getBranch(@PathVariable final Long object) {

        return new ResponseEntity<>(branchService.findBranch(object), HttpStatus.OK);

    }

    @PutMapping("/update-branch")
    public ResponseEntity<String> updateBranch(@RequestBody final Branch branch) {

        branchService.updateBranch(branch);

        return new ResponseEntity<>("BRANCH SUCCESSFULLY UPDATED", HttpStatus.OK);
    }

    @PutMapping("/update-more-branches")
    public ResponseEntity<String> updateBranches(@RequestBody final List<Branch> branches) {
        branchService.updateBranches(branches);
        return new ResponseEntity<>("BRANCHES SUCCESSFULLY UPDATED", HttpStatus.OK);
    }


    @DeleteMapping("/delete-branch/{branchId}")
    public ResponseEntity<String> deleteBranch(@PathVariable("branchId") final Long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>("BRANCH SUCCESSFULLY DELETED", HttpStatus.OK);
    }

    @DeleteMapping("/delete-more-branches")
    public ResponseEntity<String> deleteBranches(@RequestBody final List<Branch> branches) {
        branchService.deleteBranches(branches);
        return new ResponseEntity<>("LIST OF BRANCHES SUCCESSFULLY DELETED", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllBranches() {
        branchService.deleteAllBranches();
        return new ResponseEntity<>("ALL BRANCHES SUCCESSFULLY DELETED", HttpStatus.OK);
    }


}
