package com.wxj.web.admin;
import com.wxj.po.Blog;
import com.wxj.po.User;
import com.wxj.service.BlogService;
import com.wxj.service.TypeService;
import com.wxj.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRCT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public  String input(Model model){
        model.addAttribute("types",typeService.listType());


        model.addAttribute("blog",new Blog());
        return INPUT;
    }



    @GetMapping("/blogs/{id}/input")
    public  String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());

        model.addAttribute("blog",blogService.getBlog(id));
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));

        Blog b;

        if(blog.getId()==null){
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if(b==null){
            attributes.addFlashAttribute("message","????????????");
        } else {
            attributes.addFlashAttribute("message","????????????");
        }
        return REDIRCT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","????????????");
        return REDIRCT_LIST;
    }
}
