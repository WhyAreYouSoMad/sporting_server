package shop.mtcoding.sporting_server.core.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import shop.mtcoding.sporting_server.core.exception.CustomValidationException;

@Component
@Aspect
public class CustomValidationAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Around("postMapping() || putMapping()") // joinPoint의 전후 제어
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinPoint의 매개변수
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    // Map<String, String> errorMap = new HashMap<>();

                    // for (FieldError error : bindingResult.getFieldErrors()) {
                    // errorMap.put(error.getField(), error.getDefaultMessage());
                    // }
                    throw new CustomValidationException(bindingResult.getFieldErrors().get(0).getDefaultMessage());
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 정상적으로 해당 메서드를 실행해라!!
    }
}

/**
 * get, delete, post (body), put (body)
 */