package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.Impl.UserServiceImpl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.CommonResult;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.Impl.ActivityServiceImpl;
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
@RequestMapping(value = "/workbench/activity")
public class ActivityController extends HttpServlet {

    @Resource
    UserService us;

    @Resource
    ActivityService as;

   /* @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动控制器");

        String servletPath = request.getServletPath();

        if("/workbench/activity/getUserList.do".equals(servletPath)){
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(servletPath)){
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(servletPath)){
            pageList(request,response);
        }else if("/workbench/activity/delete.do".equals(servletPath)){
            delete(request,response);
        }else if("/workbench/activity/getUserListAndActivity.do".equals(servletPath)){
            getUserListAndActivity(request,response);
        } else if("/workbench/activity/update.do".equals(servletPath)){
            update(request,response);
        } else if("/workbench/activity/detail.do".equals(servletPath)){
            detail(request,response);
        } else if("/workbench/activity/getRemarkListByAid.do".equals(servletPath)){
            getRemarkListByAid(request,response);
        } else if("/workbench/activity/deleteRemark.do".equals(servletPath)){
            deleteRemark(request,response);
        }else if("/workbench/activity/saveRemark.do".equals(servletPath)){
            saveRemark(request,response);
        }else if("/workbench/activity/updateRemark.do".equals(servletPath)){
            updateRemark(request,response);
        }else if("/workbench/activity/xxx.do".equals(servletPath)){
            //xxx(request,response);
        }else if("/workbench/activity/xxx.do".equals(servletPath)){
            //xxx(request,response);
        }else if("/workbench/activity/xxx.do".equals(servletPath)){
            //xxx(request,response);
        }
    }*/

    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    private Map<String,Object> updateRemark(HttpServletRequest request, HttpServletResponse response,
                                            String noteContent, String id) {
        System.out.println("进入修改加备注操作");

        //String noteContent = request.getParameter("noteContent");
        //String id = request.getParameter("id");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setEditFlag(editFlag);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.updateRemark(ar);

        Map<String,Object> map =new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);

        //PrintJson.printJsonObj(response,map);
        return map;
    }

    @RequestMapping(value = "/saveRemark.do")
    @ResponseBody
    private Map<String,Object> saveRemark(HttpServletRequest request, HttpServletResponse response,
                                          String noteContent, String activityId) {

        System.out.println("进入到添加备注操作");

        //String noteContent = request.getParameter("noteContent");
        //String activityId = request.getParameter("activityId");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String id = UUIDUtil.getUUID();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        ar.setActivityId(activityId);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.saveRemark(ar);

        Map<String,Object> map =new HashMap<String,Object>();
        map.put("success",flag);
        map.put("ar",ar);

        //PrintJson.printJsonObj(response,map);
        return map;

    }

    @RequestMapping(value = "/deleteRemark.do")
    @ResponseBody
    private Map<String,Boolean> deleteRemark(HttpServletRequest request, HttpServletResponse response,
                                             String id) {
        System.out.println("删除备注操作");

        //String id = request.getParameter("id");

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.deleteRemark(id);

        //PrintJson.printJsonFlag(response,flag);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping(value = "/getRemarkListByAid.do")
    @ResponseBody
    private List<ActivityRemark> getRemarkListByAid(HttpServletRequest request, HttpServletResponse response,
                                                    String activityId) {
        System.out.println("根据市场活动id，取得备注信息列表");

        //String activityId = request.getParameter("activityId");

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        return arList;
        //PrintJson.printJsonObj(response,arList);

    }

    @RequestMapping(value = "/detail.do")
    private ModelAndView detail(HttpServletRequest request, HttpServletResponse response,
                                String id) throws ServletException, IOException {
        System.out.println("进入到跳转到详细页面的操作");

        ModelAndView mv = new ModelAndView();

        //String id = request.getParameter("id");

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = as.detail(id);

        //request.setAttribute("a",a);
        mv.addObject("a",a);

        //request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return mv;
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    private Map<String,Boolean> update(HttpServletRequest request, HttpServletResponse response,
                                       String id, String owner, String name, String startDate,
                                       String endDate, String cost, String description) {

        System.out.println("执行市场活动修改操作");


        /*String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        *///修改时间：当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人：当前登录用户
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.update(a);

        //PrintJson.printJsonFlag(response,flag);

        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;


    }



    @RequestMapping(value = "/getUserListAndActivity.do")
    @ResponseBody
    private Map<String,Object> getUserListAndActivity(HttpServletRequest request, HttpServletResponse response,
                                                      String id) {
        System.out.println("进入到查询用户信息列表和根据市场活动id查询单条记录的操作");

        //String id = request.getParameter("id");

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*

            总结：
                controller调用service的方法，返回值应该是什么
                你得想一想前端要什么，就要从service层取什么

            前端需要的，管业务层去要
            uList
            a

            以上两项信息，复用率不高，我们选择使用map打包这两项信息即可
            map

         */
        Map<String,Object> map = as.getUserListAndActivity(id);

        //PrintJson.printJsonObj(response, map);
        return map;

    }

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    private Map<String,Boolean> delete(HttpServletRequest request, HttpServletResponse response,
                                       String[] id) {
        System.out.println("执行市场活动的删除操作");

        //String[] ids = request.getParameterValues("id");
        String[] ids = id;
        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.delete(ids);

        //PrintJson.printJsonFlag(response,flag);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;

    }

    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    private PaginationVO<Activity> pageList(HttpServletRequest request, HttpServletResponse response,
                                            String pageNo,String pageSize,String name, String owner, String startDate,String endDate){



        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

/*        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");*/
        int pageNoInt = Integer.valueOf(pageNo);
        //每页展现的记录数
        //String pageSizeStr = request.getParameter("pageSize");
        int pageSizeNum = Integer.valueOf(pageSize);
        //计算出略过的记录数
        int skipCount = (pageNoInt-1)*pageSizeNum;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSizeNum);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*

            前端要： 市场活动信息列表
                    查询的总条数

                    业务层拿到了以上两项信息之后，如果做返回
                    map
                    map.put("dataList":dataList)
                    map.put("total":total)
                    PrintJSON map --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}


                    vo
                    PaginationVO<T>
                        private int total;
                        private List<T> dataList;

                    PaginationVO<Activity> vo = new PaginationVO<>;
                    vo.setTotal(total);
                    vo.setDataList(dataList);
                    PrintJSON vo --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}


                    将来分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便




         */
        PaginationVO<Activity> vo = as.pageList(map);

        //vo--> {"total":100,"dataList":[{市场活动1},{2},{3}]}
        //PrintJson.printJsonObj(response, vo);
        return vo;



    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    private Map<String,Boolean> save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建时间：当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        //ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
        //PrintJson.printJsonFlag(response,flag);


    }

    @RequestMapping("/getUserList.do")
    @ResponseBody
    private List<User> getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        //CommonResult cr = null;
        //UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        //cr = new CommonResult();
        return uList;
    }


}
