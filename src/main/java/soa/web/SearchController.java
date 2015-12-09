package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        HashMap<String, Object> opts = new HashMap<String, Object>();

        String [] queries = q.split(" max:");
        opts.put("CamelTwitterKeywords", queries[0]);
        if(queries.length == 2) opts.put("CamelTwitterCount", queries[1]);

        return producerTemplate.requestBodyAndHeader("direct:search", "", opts);
    }
}