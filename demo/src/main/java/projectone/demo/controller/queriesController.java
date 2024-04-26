package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import projectone.demo.repository.SalesDataRepository;
import projectone.demo.repository.SalesTrendsRepository;

@RequestMapping(value = "queries")
@Controller
class queriesController {
    private final SalesTrendsRepository salesTrendsRepo;
    private final SalesDataRepository salesDataRepo;

    queriesController(SalesTrendsRepository salesTrendsRepo, SalesDataRepository salesDataRepo)
    {
        this.salesDataRepo = salesDataRepo;
        this.salesTrendsRepo = salesTrendsRepo;
    }
    String queries(Model TrendModel, Model DataModel)
    {
        TrendModel.addAttribute("trends", TrendModel);
        DataModel.addAttribute("data", DataModel);
        return "queries";
    }
    
}
