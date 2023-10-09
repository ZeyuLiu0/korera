package com.example.korera.service;

import com.example.korera.entity.Formula;
import com.example.korera.exceptions.CreationException;
import com.example.korera.exceptions.FormulaNotFoundException;
import com.example.korera.repository.FormulaRep;
import jakarta.validation.Constraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormulaServiceImp implements FormulaService {
    private final FormulaRep formulaRep;

    @Autowired
    public FormulaServiceImp(FormulaRep formulaRep) {
        this.formulaRep = formulaRep;
    }


    @Override
    public Formula createFormula(Formula formula) {
        formulaRep.save(formula);
        Optional<Formula> formula1 = formulaRep.findById(formula.getFormulaId());
        if(formula1.isEmpty()){
            throw new CreationException("cannot create");
        }
        return formula1.get();
    }

    @Override
    public void deleteFormulaById(int id) {
        Formula Formula = getFormulaById(id);
        if (Formula == null) {
            throw new FormulaNotFoundException("Formula is not found");
        }
        formulaRep.deleteById(id);
    }

    @Override
    public Formula updateFormula(Formula formula) {
        Optional<Formula> optional = formulaRep.findById(formula.getFormulaId());
        if (optional.isEmpty()) {
            throw new FormulaNotFoundException("Formula is not found");
        }
        formulaRep.save(formula);
        return formula;
    }

    @Override
    public Formula getFormulaById(int id) {
        Optional<Formula> Formula = formulaRep.findById(id);
        if (Formula.isEmpty()) {
            throw new FormulaNotFoundException("Formula is not found");
        }
        return Formula.get();
    }

    @Override
    public List<Formula> getAll() {
        return formulaRep.findAll();
    }
}
