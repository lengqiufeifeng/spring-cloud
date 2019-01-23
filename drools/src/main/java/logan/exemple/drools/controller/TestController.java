package logan.exemple.drools.controller;

import logan.exemple.drools.base.KieUtils;
import logan.exemple.drools.model.Address;
import logan.exemple.drools.model.fact.AddressCheckResult;
import logan.exemple.drools.service.ReloadDroolsRules;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by logan on 2017/11/9.
 * qq：425018553
 */
@RequestMapping("/test" )
@Controller
public class TestController {

    @Resource
    private ReloadDroolsRules rules;

    @ResponseBody
    @RequestMapping("/address" )
    public String test() {
        KieSession kieSession = KieUtils.getKieContainer().newKieSession();

        Address address = new Address();
        address.setPostcode("99251" );
        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
//        int ruleFiredCount = kieSession.fireAllRules();
        int ruleFiredCount = kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return false;
            }
        });
        String msg = "触发了" + ruleFiredCount + "条规则";

        if (result.isPostCodeResult()) {
            msg = msg + "————规则校验通过";
        }

        kieSession.dispose();
        return msg;
    }

    @ResponseBody
    @RequestMapping("/reload" )
    public String reload() throws IOException {
        rules.reload();
        return "ok";
    }
}
