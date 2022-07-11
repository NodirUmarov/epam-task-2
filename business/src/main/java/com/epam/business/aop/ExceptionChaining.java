package com.epam.business.aop;

import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityIsUsingException;
import com.epam.business.exception.EntityNameNotFountException;
import com.epam.data.exception.DataNotFoundException;
import com.epam.data.exception.DuplicateEntityException;
import com.epam.data.exception.DataIsUsingException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionChaining {

    @Pointcut("execution(* com.epam.business.service.*.*(..))")
    private void servicePointCut(){}

    @Pointcut("execution(* com.epam.business.service.*.*ByName(..))")
    private void servicePointCutByName(){}

    @Pointcut("execution(* com.epam.business.service.*.delete*(..))")
    private void servicePointCutDelete() {}

    @Pointcut("execution(* com.epam.business.service.*.*update*(Long))")
    private void servicePointCutUpdate() {}

    @AfterThrowing(pointcut = "servicePointCut()", throwing = "duplicateEntityException")
    public void entityExistsExceptionThrow(DuplicateEntityException duplicateEntityException) {
        throw new EntityExistsException();
    }

    @AfterThrowing(pointcut = "servicePointCutByName()", throwing = "dataNotFoundException")
    public void entityNameNotFountExceptionThrow(DataNotFoundException dataNotFoundException) {
        throw new EntityNameNotFountException();
    }

    @AfterThrowing(pointcut = "servicePointCutUpdate()", throwing = "dataNotFoundException")
    public void entityIdNotFoundExceptionThrow(DataNotFoundException dataNotFoundException) {
        throw new EntityIdNotFoundException();
    }

    @AfterThrowing(pointcut = "servicePointCutDelete()", throwing = "dataIsUsingException")
    public void entityIsUsingExceptionThrow(DataIsUsingException dataIsUsingException) {
        throw new EntityIsUsingException();
    }
    
    

}
