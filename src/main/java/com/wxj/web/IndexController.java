package com.wxj.web;

import com.wxj.service.BlogService;
import com.wxj.service.CommentService;
import com.wxj.service.MessageService;
import com.wxj.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {



    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
   public String index(@PageableDefault(size = 2,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){



        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType(6));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(4));

       System.out.println("--index----");
        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        //int i = 9/0;
//        String blog =null;
//        if(blog == null) {
//            throw new NotFoundException("博客不存在");
//        }
        System.out.println("--index----");
        return "blog";
    }


    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("commentnum",commentService.countComment());
        model.addAttribute("blognum",blogService.countBlog());
        model.addAttribute("messagenum",messageService.countMessage());
        //model.addAttribute("viewnum",viewnum);
        //model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

    @PostMapping("/footer/newbloglist")
    public String newbloglist(@PageableDefault(size = 2,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        System.out.println("--index-fdsfdsfsdfdsfsdfdsfsdfsdfdsfsdfdsf----------------------------");
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType(6));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(4));
        return "index :: bloglist";
    }

}
