package com.shawn.shopproject.util;

import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.LocalDateTime;
import java.util.List;

@Component
@WebListener
public class SessionListener implements HttpSessionListener {

    @Autowired
    private ProductService productService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(LocalDateTime.now() + "-" + se.getSession().getId() + "-該session被建立");
    }

    // session 監聽器 在session銷毀前，將購物車資料存進資料庫
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        List<Integer> cartlist = (List<Integer>) se.getSession().getAttribute("cart");
        Member member = (Member) se.getSession().getAttribute("memberdata");

        productService.storeCart(member, cartlist);

        System.out.println(LocalDateTime.now() + "-" + se.getSession().getId() + "-該session銷毀");
    }
}




