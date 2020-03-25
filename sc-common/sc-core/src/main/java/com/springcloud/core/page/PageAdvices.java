package com.springcloud.core.page;

import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * <p>分页拦截器</p>
 * <p>创建日期：2020-03-24</p>
 *
 * @author 刘昊鑫
 */
@Component
@Aspect
public class PageAdvices {

    @Before("execution(* *ByPage(..))")
    public void before(JoinPoint jp) {
        if (PaginationContext.isPage() != null && PaginationContext.isPage()) {
            int pageNum = PaginationContext.getPageNum();
            int pageSize = PaginationContext.getPageSize();
            PageHelper.startPage(pageNum, pageSize);
        }
    }

}
