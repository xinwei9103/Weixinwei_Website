package xinwei.web.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import xinwei.web.bean.News;
import xinwei.web.bean.User;
import xinwei.web.service.NewsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei on 1/29/2017.
 */
@Controller
public class NewsController {

    private NewsService newsService;

    public NewsService getNewsService() {
        return newsService;
    }

    @Resource
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/content/{page}")
    public String content(@PathVariable String page, ModelMap map, HttpSession session) {
        //System.out.println(page);
        QueryResultPage<News> newsQueryResultPage = null;
        List<News> result = null;
        Map<String, AttributeValue> nextPageKey = null;
        Map<String, AttributeValue> lastPageKey = null;
        if (page.equals("next")) {
            lastPageKey = (Map<String, AttributeValue>) session.getAttribute("nextPage");
            newsQueryResultPage = newsService.getNextPage(lastPageKey);
            nextPageKey = newsQueryResultPage.getLastEvaluatedKey();
            result = newsQueryResultPage.getResults();
            /*
            System.out.println();
            for(News news:result){
                System.out.println(news.getNewsContent().getMessage()+"::"+news.getDate());
            }
            */
        } else if (page.equals("previous")) {
            nextPageKey = (Map<String, AttributeValue>) session.getAttribute("lastPage");
            newsQueryResultPage = newsService.getPrePage(nextPageKey);
            if (newsQueryResultPage == null) {
                return "redirect:/content/first";
            } else {
                lastPageKey = newsQueryResultPage.getLastEvaluatedKey();
                result = newsQueryResultPage.getResults();

                result.remove(result.size() - 1);
                result = Lists.reverse(result);
                /*
                System.out.println();
                for(News news:result){
                    System.out.println(news.getNewsContent().getMessage()+"::"+news.getDate());
                }
                */

            }
        } else {
            newsQueryResultPage = newsService.getFirstPage();
            result = newsQueryResultPage.getResults();
            /*
            System.out.println();
            for(News news:result){
                System.out.println(news.getNewsContent().getMessage()+"::"+news.getDate());
            }
            */
            nextPageKey = newsQueryResultPage.getLastEvaluatedKey();
        }
        session.setAttribute("nextPage", nextPageKey);
        if (nextPageKey == null) {
            map.addAttribute("hasNext", false);
        } else {
            map.addAttribute("hasNext", true);
        }
        session.setAttribute("lastPage", lastPageKey);
        if (lastPageKey == null) {
            map.addAttribute("hasPre", false);
        } else {
            map.addAttribute("hasPre", true);
        }
        map.addAttribute("pageContent", result);
        return "content";
    }

    @RequestMapping("/createNews")
    public String createNews(@SessionAttribute User user, @RequestParam String message, MultipartRequest request) {
        List<MultipartFile> files = request.getFiles("files");
        //System.out.println(files.size());
        newsService.createNews(user,message,files);
        return "redirect:/content/first";
    }

    @RequestMapping("/save")
    public String save(@RequestParam(required = false) MultipartFile[] files) {
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return "content";
    }

    @RequestMapping("/delete/{email}/{time}")
    public String delete(@PathVariable String email, @PathVariable long time) {
        if (email == null || email.equals("") || time == 0) {
            return "redirect:/content/first";
        }
        newsService.deleteNews(email, time);

        return "redirect:/content/first";
    }
}
