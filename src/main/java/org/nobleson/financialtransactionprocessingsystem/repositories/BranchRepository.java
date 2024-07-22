package org.nobleson.financialtransactionprocessingsystem.repositories;

import org.nobleson.financialtransactionprocessingsystem.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByBranchId(Long branchId);
    boolean existsBranchByBranchName(String branchName);
    Optional<Branch> findByBranchName(String branchName);

    void deleteByBranchName(String branchName);

    //Optional<Branch> existsBranchByBranchIdOrBranchName(Object object);
}
