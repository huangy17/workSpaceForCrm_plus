package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.Impl.UserServiceImpl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.Impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.Impl.ClueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangyb
 * @create 2021-07-11 0:23
 */
@Controller
@RequestMapping(value = "/workbench/clue")
public class ClueController extends HttpServlet {


    @Resource
    UserService us;

    @Resource
    ClueService cs;

    @Resource
    ActivityService as;


    /*@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索控制器");

        String servletPath = request.getServletPath();

        if("/workbench/clue/getUserList.do".equals(servletPath)){
            getUserList(request,response);
        }else if("/workbench/clue/save.do".equals(servletPath)){
            save(request,response);
        }else if("/workbench/clue/detail.do".equals(servletPath)){
            detail(request,response);
        }else if("/workbench/clue/getActivityListByClueId.do".equals(servletPath)){
            getActivityListByClueId(request,response);
        }else if("/workbench/clue/unbund.do".equals(servletPath)){
            unbund(request,response);
        }else if("/workbench/clue/getActivityListByNameAndExcludeClueId.do".equals(servletPath)){
            getActivityListByNameAndExcludeClueId(request,response);
        }else if("/workbench/clue/bund.do".equals(servletPath)){
            bund(request,response);
        }else if("/workbench/clue/getActivityListByName.do".equals(servletPath)){
            getActivityListByName(request,response);
        }else if("/workbench/clue/convert.do".equals(servletPath)){
            convert(request,response);
        }else if("/workbench/clue/xxx.do".equals(servletPath)){
            //xxx(request,response);
        }else if("/workbench/clue/xxx.do".equals(servletPath)){
            //xxx(request,response);
        }
    }
*/
    @RequestMapping(value = "/convert.do")
    private ModelAndView convert(HttpServletRequest request, HttpServletResponse response,
                                 String clueId, String flag) throws IOException {
        System.out.println("执行线索转换的操作");

        ModelAndView mv = new ModelAndView();
        //String clueId = request.getParameter("clueId");

        //接收是否需要创建交易的标记
        //String flag = request.getParameter("flag");

        Tran t = null;
        String createBy = ((User)request.getSession().getAttribute("user")).getName();


        //如果需要创建交易
        if("a".equals(flag)){
            t=new Tran();

            //接收交易表单中的参数
            //接收交易表单中的参数
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();


            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }

        //ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

         /*
            为业务层传递的参数：

            1.必须传递的参数clueId，有了这个clueId之后我们才知道要转换哪条记录
            2.必须传递的参数t，因为在线索转换的过程中，有可能会临时创建一笔交易（业务层接收的t也有可能是个null）
         */

        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1) {
            //response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        return mv;
    }

    @RequestMapping(value = "/getActivityListByName.do")
    @ResponseBody
    private List<Activity> getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表（根据名称模糊查）");
        String aname = request.getParameter("aname");

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByName(aname);

        //PrintJson.printJsonObj(response,aList);
        return aList;


    }

    @RequestMapping(value = "/bund.do")
    @ResponseBody
    private Map<String,Boolean> bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行关联市场活动的操作");

        String cid=request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        //ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.bund(cid,aids);

        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
        //PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping(value = "/getActivityListByNameAndExcludeClueId.do")
    @ResponseBody
    private List<Activity> getActivityListByNameAndExcludeClueId(HttpServletRequest request, HttpServletResponse response,
                                                       String aname, String clueId) {
        System.out.println("查询市场活动列表（更具名称模糊查询+排除掉已经关联指定线索的列表）");
        //String aname = request.getParameter("aname");
        //String clueId = request.getParameter("clueId");

        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList = as.getActivityListByNameAndExcludeClueId(map);

        //PrintJson.printJsonObj(response,aList);
        return aList;
    }
    @RequestMapping(value = "/unbund.do")
    @ResponseBody
    private Map<String,Boolean> unbund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行解除关联操作");
        String id = request.getParameter("id");

        //ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        //PrintJson.printJsonFlag(response,flag);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;

    }

    @RequestMapping(value = "/getActivityListByClueId.do")
    @ResponseBody
    private List<Activity> getActivityListByClueId(HttpServletRequest request, HttpServletResponse response,
                                                   String clueId) {

        System.out.println("根据线索id获取市场活动列表");

        String id = clueId;

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListById(id);

        return aList;

        //PrintJson.printJsonObj(response, aList);

    }

    @RequestMapping(value = "/detail.do")
    private ModelAndView detail(HttpServletRequest request, HttpServletResponse response,
                        String id)throws ServletException, IOException {

        System.out.println("跳转到线索详细信息页");

        ModelAndView mv = new ModelAndView();
        //String id = request.getParameter("id");

        //ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = cs.detail(id);

        //request.setAttribute("c", c);
        //request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);

        mv.addObject("c",c);
        mv.setViewName("forward:/workbench/clue/detail.jsp");
        return mv;

    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    private Map<String,Boolean> save(HttpServletRequest request, HttpServletResponse response,
                      Clue c) {

        System.out.println("执行线索添加操作");

        String id = UUIDUtil.getUUID();
//        String fullname = request.getParameter("fullname");
//        String appellation = request.getParameter("appellation");
//        String owner = request.getParameter("owner");
//        String company = request.getParameter("company");
//        String job = request.getParameter("job");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String website = request.getParameter("website");
//        String mphone = request.getParameter("mphone");
//        String state = request.getParameter("state");
//        String source = request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
//        String description = request.getParameter("description");
//        String contactSummary = request.getParameter("contactSummary");
//        String nextContactTime = request.getParameter("nextContactTime");
//        String address = request.getParameter("address");

        //Clue c = new Clue();
        c.setId(id);
//        c.setAddress(address);
//        c.setWebsite(website);
//        c.setState(state);
//        c.setSource(source);
//        c.setPhone(phone);
//        c.setOwner(owner);
//        c.setNextContactTime(nextContactTime);
//        c.setMphone(mphone);
//        c.setJob(job);
//        c.setFullname(fullname);
//        c.setEmail(email);
//        c.setDescription(description);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
//        c.setContactSummary(contactSummary);
//        c.setCompany(company);
//        c.setAppellation(appellation);

        //ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
        //PrintJson.printJsonFlag(response, flag);


    }


    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    private List<User> getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        //UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        return uList;
        //PrintJson.printJsonObj(response,uList);
    }


}