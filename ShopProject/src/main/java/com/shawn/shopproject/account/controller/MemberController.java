package com.shawn.shopproject.account.controller;

import com.shawn.shopproject.account.dto.MemberLoginRequest;
import com.shawn.shopproject.account.dto.MemberQueryParam;
import com.shawn.shopproject.account.dto.MemberRegisterRequest;
import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.account.service.MemberService;
import com.shawn.shopproject.shop.dto.CartList;
import com.shawn.shopproject.shop.service.ProductService;
import com.shawn.shopproject.util.mailservice.MailBulider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MemberController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MailBulider mailBulider;

    @Autowired
    private ProductService productService;


    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody @Valid MemberRegisterRequest memberRegisterRequest) {

        Integer memberid = memberService.register(memberRegisterRequest);

        Member member = memberService.getMemberById(memberid);

        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/select_member/{memberid}")
    public ResponseEntity<Member> getMemberById(@PathVariable Integer memberid) {

        Member member = memberService.getMemberById(memberid);

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Member> login(@RequestBody @Valid MemberLoginRequest memberLoginRequest,
                                        HttpServletRequest request) {

        Member member = memberService.login(memberLoginRequest);

        if (member != null) {
            // 登入時取出購物車內容放入session
            List<CartList> productlist = productService.getProductsByMemberId(member.getUserId());
            List<Integer> list = new ArrayList<>();
            if (productlist != null) {
                for (int i = 0; i < productlist.size(); i++) {
                    for (int j = 0; j < productlist.get(i).getQuantity(); j++) {
                        list.add(productlist.get(i).getProductid());
                    }
                }
            }
            // 創建session
            HttpSession session = request.getSession();
            session.setAttribute("loggedin", true);
            session.setAttribute("memberdata", member);
            session.setAttribute("cart", list);
        }
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @GetMapping("/member/checklogin")
    public ResponseEntity<?> checklogin(@SessionAttribute("loggedin") Boolean logged,
                                        @SessionAttribute("memberdata") Member member) {


        if (logged == true) {
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/member/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/checkemail")
    public ResponseEntity<?> checkMail(@RequestBody MemberQueryParam memberQueryParam) {

        if (memberService.checkMail(memberQueryParam.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/sendmail")
    public void sendMail(@RequestBody MemberQueryParam memberQueryParam) {
        mailBulider.sendSimpleEmail(memberQueryParam);
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<?> forgetpassword(@RequestBody MemberQueryParam memberQueryParam) {

        mailBulider.sendForgetMail(memberQueryParam);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/updatepassword/{randancode}")
    public ModelAndView redirectPassword(@PathVariable String randancode) {
        return new ModelAndView(new RedirectView("static/util/updatepassword.html"));
    }

    @PostMapping("/updatepassword")
    public ResponseEntity<?> updatepassword(@RequestBody MemberLoginRequest memberLoginRequest) {

        memberService.updatePassword(memberLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}

