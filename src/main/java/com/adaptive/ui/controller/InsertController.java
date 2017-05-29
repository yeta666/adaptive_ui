package com.adaptive.ui.controller;

import com.adaptive.ui.domain1.*;
import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.repository1.*;
import com.adaptive.ui.repository2.TrainArrayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 用于插入数据的类
 * Created by yeta on 2017/5/22/022.
 */
@RestController
public class InsertController {

    @Autowired
    private ReSelectCourceRepository reSelectCourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BbsPostRepository bbsPostRepository;

    @Autowired
    private BbsReplyRepository bbsReplyRepository;

    @Autowired
    private LearnProcessRecordRepository learnProcessRecordRepository;

    @Autowired
    private ReAutoTestRepository reAutoTestRepository;

    @Autowired
    private TrainArrayRepository trainArrayRepository;

    /**
     * 插入选课
     * @return
     */
    @GetMapping(value = "/insert1")
    public String insert1(){
       /* for(int xx = 17; xx < 21; xx++){ */
            //随机生成要选的课程数量
            int num = new Random().nextInt(10);
            //根据数量生成课程id
            int[] ids = randomCommon(1, 9, num);
            //根据用户id插入
            for(int i = 0; i < num; i++) {
                ReSelectcource reSelectcource = new ReSelectcource();
                reSelectcource.setRscoUserId(99);
                reSelectcource.setRscoCourId(ids[i]);
                reSelectcource.setRscoVerify(1);
                reSelectcource.setRscoTime(new Date());
                reSelectcource.setRscoMassedlearnscore(new Float(0));
                reSelectcource.setRscoLoginscore(new Float(0));
                reSelectcource.setRscoLearntimescore(new Float(0));
                reSelectcource.setRscoBbsdiscussscore(new Float(0));
                reSelectcource.setRscoSubassessscore(new Float(0));
                reSelectcource.setRscoTestscore(new Float(0));
                reSelectcource.setRscoTotalscore(new Float(0));
                reSelectcource.setRscoState(0);
                reSelectcource.setRscoUserDepaname("计算机科学与技术");
                reSelectcource.setRscoClassName("计算机1401");
                reSelectcource.setRscoGradeId(1);
                reSelectcource.setRscoGradeName("2014级");
                reSelectcource.setRscoValid(1);
                reSelectCourceRepository.save(reSelectcource);
            }
       /*}*/
        return "";
    }

    /**
     * 插入讨论
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/insert2")
    public String insert2() throws ParseException {
        /*for(int xx = 16; xx < 21; xx++){ */
            //获取选择的课程
            List<ReSelectcource> reSelectcourceList = reSelectCourceRepository.findAllByRscoUserId(99);
            for(int i = 0; i < reSelectcourceList.size(); i++){
                //获取一个课程id
                int courseId = reSelectcourceList.get(i).getRscoCourId();
                //每门课发布0-2个讨论
                int num = new Random().nextInt(3);
                //System.out.println(num);
                Bbspost bbspost = new Bbspost();
                bbspost.setBbpoCourId(courseId);
                bbspost.setBbpoUserId(99);
                bbspost.setBbpoTitle("测试");
                bbspost.setBbpoContent("测试测试测试");
                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int a = new Random().nextInt(2) + 1;
                if(a == 1){
                    bbspost.setBbpoTime(dateFormat2.parse("2017-05-22 22:36:01"));
                }else if(a == 2){
                    bbspost.setBbpoTime(dateFormat2.parse("2017-05-22 15:36:01"));
                }
                bbspost.setBbpoVisitnum(new Random().nextInt(11));
                bbspost.setBbpoReplynum(new Random().nextInt(11));
                int b = new Random().nextInt(2) + 1;
                if(b == 1){
                    bbspost.setBbpoIsbest(true);
                }else if(b == 2){
                    bbspost.setBbpoIsbest(false);
                }
                int c = new Random().nextInt(2) + 1;
                if(c == 1){
                    bbspost.setBbpoIstop(true);
                }else if(c == 2){
                    bbspost.setBbpoIstop(false);
                }
                bbspost.setBbpoHasattach(false);
                bbspost.setBbpoUserName(userRepository.findOne(99).getUserRealname());
                bbspost.setBbpoUserDepartid(2);
                if(courseId == 1){
                    bbspost.setBbpoCourName("语言文化的探源与创新");
                }else if(courseId == 2){
                    bbspost.setBbpoCourName("我的成长之路");
                }else if(courseId == 3){
                    bbspost.setBbpoCourName("植物器官和品质发育的表观遗传控制");
                }else if(courseId == 4){
                    bbspost.setBbpoCourName("平板演示");
                }else if(courseId == 5){
                    bbspost.setBbpoCourName("基于科学计算的多学科设计优化（MDO）");
                }else if(courseId == 6){
                    bbspost.setBbpoCourName("核能");
                }else if(courseId == 7){
                    bbspost.setBbpoCourName("放射性废水的生物处理方法");
                }else if(courseId == 8){
                    bbspost.setBbpoCourName("薄壁轻钢结构的发展与应用");
                }else if(courseId == 9){
                    bbspost.setBbpoCourName("有效沟通的障碍");
                }
                bbsPostRepository.save(bbspost);
            }
        /*}*/
        return "";
    }

    /**
     * 插入回复讨论
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/insert3")
    public String insert3() throws ParseException {
        /*for(int xx = 1; xx < 21; xx++){
            //一个人回复0-5个讨论
            int num = new Random().nextInt(5);
            System.out.println("num " + num);
            if(num != 0){
                //取出这个人选的所有课程的所有讨论
                //获取选了哪些课程
                List<Bbspost> list = new ArrayList<Bbspost>();
                List<ReSelectcource> reSelectcourceList = reSelectCourceRepository.findAllByRscoUserId(xx);
                for(int i = 0; i < reSelectcourceList.size(); i++){
                    //获取一门课程
                    int courseId = reSelectcourceList.get(i).getRscoCourId();
                    //根据课程id去看该课程有哪些讨论
                    List<Bbspost> bbspostList = bbsPostRepository.findAllByBbpoCourId(courseId);
                    list.addAll(bbspostList);
                }
                System.out.println("size " + list.size());
                if(list.size() != 0){
                    //随机生成num个讨论下标，回复该下标的讨论
                    for(int i = 0; i < num; i++){
                        int a = new Random().nextInt(list.size());
                        Bbspost bbspost = list.get(a);
                        //回复这个讨论
                        Bbsreply bbsreply = new Bbsreply();
                        bbsreply.setBbreBbpoId(bbspost.getBbpoId());
                        bbsreply.setBbreUserId(xx);
                        bbsreply.setBbreContent("<p>回复回复回复<br></p>");
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int b = new Random().nextInt(2) + 1;
                        if(b == 1){
                            bbsreply.setBbreTime(dateFormat2.parse("2017-05-22 22:36:01"));
                        }else if(b == 2){
                            bbsreply.setBbreTime(dateFormat2.parse("2017-05-22 15:36:01"));
                        }
                        bbsreply.setBbreHasattach(false);
                        bbsReplyRepository.save(bbsreply);
                    }
                }
            }
        }*/
        return "";
    }

    /**
     * 修改回复讨论的次数
     * @return
     */
    @GetMapping(value = "/insert4")
    public String insert4() {
        /*//获取所有讨论
        List<Bbspost> bbspostList = bbsPostRepository.findAll();
        for(int i = 0; i < bbspostList.size(); i++){
            //获取一条讨论
            Bbspost bbspost = bbspostList.get(i);
            //根据id查找有多少回复
            List<Bbsreply> bbsreplyList = bbsReplyRepository.findAllByBbreBbpoId(bbspost.getBbpoId());
            //修改回复数目
            bbspost.setBbpoReplynum(bbsreplyList.size());
            //更新
            bbsPostRepository.save(bbspost);
        }*/
        return "";
    }

    /**
     * 插入插入学习过程
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/insert5")
    public String insert5() throws ParseException {
        /*for(int xx = 1; xx < 21; xx++){
            //获取该用户选的所有课程
            List<ReSelectcource> reSelectcourceList = reSelectCourceRepository.findAllByRscoUserId(xx);
            if(reSelectcourceList.size() != 0){
                //随机学习0-reSelectcourceList.size()个课程
                int a1 = new Random().nextInt(reSelectcourceList.size());
                if(a1 != 0){
                    for(int i = 0; i < a1; i++) {
                        //获取一个课程id
                        int a2 = new Random().nextInt(reSelectcourceList.size());
                        int courseId = reSelectcourceList.get(a2).getRscoCourId();
                        int cId = 0;
                        int rId = 0;
                        //确定章节id和资源id
                        if(courseId == 1){
                            cId = 9;
                            rId = 8;
                        }else if(courseId == 2){
                            cId = 8;
                            rId = 6;
                        }else if(courseId == 3){
                            cId = 10;
                            rId = 9;
                        }else if(courseId == 4){
                            cId = 7;
                            rId = 5;
                        }else if(courseId == 5){
                            cId = 6;
                            rId = 10;
                        }else if(courseId == 6){
                            int a = new Random().nextInt(2) + 1;
                            if(a == 1){
                                cId = 4;
                                rId = 1;
                            }else if(a == 2){
                                cId = 5;
                                rId = 4;
                            }
                        }else if(courseId == 7){
                            cId = 3;
                            rId = 2;
                        }else if(courseId == 8){
                            cId = 2;
                            rId = 7;
                        }else if(courseId == 9){
                            cId = 1;
                            rId = 3;
                        }
                        //插入
                        Learnprocessrecord learnprocessrecord = new Learnprocessrecord();
                        learnprocessrecord.setLpreUserId(xx);
                        learnprocessrecord.setLpreChapId(cId);
                        learnprocessrecord.setLpreContent("");
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int b = new Random().nextInt(2) + 1;
                        if(b == 1){
                            learnprocessrecord.setLpreBegintime(dateFormat2.parse("2017-05-22 22:36:01"));
                        }else if(b == 2){
                            learnprocessrecord.setLpreBegintime(dateFormat2.parse("2017-05-22 15:36:01"));
                        }
                        //learnprocessrecord.setLpreEndtime();
                        //learnprocessrecord.setLpreLearntime();
                        learnprocessrecord.setLpreResoId(rId);
                        learnProcessRecordRepository.save(learnprocessrecord);
                    }
                }
            }
        }*/
        return "";
    }

    /**
     * 插入测试
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/insert6")
    public String insert6() throws ParseException {
       /* for (int xx = 1; xx < 21; xx++){
            //判断用户是否选择了可以测试的这两门课
            List<ReSelectcource> reSelectcourceList = reSelectCourceRepository.findAllByRscoUserId(xx);
            if(reSelectcourceList.size() != 0){
                for(int i = 0; i < reSelectcourceList.size(); i++){
                    ReAutotest reAutotest = new ReAutotest();
                    if(reSelectcourceList.get(i).getRscoCourId() == 2){
                        reAutotest.setRateExinId(3);
                        reAutotest.setRateIsfinished(true);
                        reAutotest.setRateScore(new Float(new Random().nextInt(100)));
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int b = new Random().nextInt(2) + 1;
                        if(b == 1){
                            reAutotest.setRateStarttime(dateFormat2.parse("2017-05-22 22:36:01"));
                            reAutotest.setRateSubmittime(dateFormat2.parse("2017-05-22 22:40:01"));
                        }else if(b == 2){
                            reAutotest.setRateStarttime(dateFormat2.parse("2017-05-22 15:36:01"));
                            reAutotest.setRateSubmittime(dateFormat2.parse("2017-05-22 15:40:01"));
                        }
                        reAutotest.setRateUserId(xx);
                        reAutoTestRepository.save(reAutotest);
                    }else if(reSelectcourceList.get(i).getRscoCourId() == 9) {
                        reAutotest.setRateExinId(1);
                        reAutotest.setRateIsfinished(true);
                        reAutotest.setRateScore(new Float(new Random().nextInt(100)));
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int b = new Random().nextInt(2) + 1;
                        if(b == 1){
                            reAutotest.setRateStarttime(dateFormat2.parse("2017-05-22 22:36:01"));
                            reAutotest.setRateSubmittime(dateFormat2.parse("2017-05-22 22:40:01"));
                        }else if(b == 2){
                            reAutotest.setRateStarttime(dateFormat2.parse("2017-05-22 15:36:01"));
                            reAutotest.setRateSubmittime(dateFormat2.parse("2017-05-22 15:40:01"));
                        }
                        reAutotest.setRateUserId(xx);
                        reAutoTestRepository.save(reAutotest);
                    }
                }
            }
        }*/
        return "";
    }

    /**
     * 插入所选课程分数
     * @return
     */
    @GetMapping(value = "/insert7")
    public String insert7() {
        /*for(int xx = 1; xx < 21; xx++){
            //获取所有选的课程
            List<ReSelectcource> reSelectcourceList = reSelectCourceRepository.findAllByRscoUserId(xx);
            for(int i = 0; i < reSelectcourceList.size(); i++){
                //获取一门课程
                ReSelectcource reSelectcource = reSelectcourceList.get(i);
                reSelectcource.setRscoLoginscore(new Float(new Random().nextInt(21)));
                reSelectcource.setRscoLearntimescore(new Float(new Random().nextInt(41)));
                reSelectcource.setRscoBbsdiscussscore(new Float(new Random().nextInt(21)));
                reSelectcource.setRscoSubassessscore(new Float(new Random().nextInt(21)));
                reSelectCourceRepository.save(reSelectcource);
            }
        }*/
        return "";
    }

    /**
     * 插入训练集
     * @return
     */
    @GetMapping(value = "/insert8")
    public String insert8() {
        for(int i = 0; i < 20; i++){
            TrainArray trainArray = new TrainArray();
            //性别
            if(new Random().nextInt(2) + 1 == 1){
                trainArray.setGender("男");
            }else{
                trainArray.setGender("女");
            }
            //入学时间
            if(new Random().nextInt(2) + 1 == 1){
                trainArray.setEntranceTime("≥2");
            }else{
                trainArray.setEntranceTime("<2");
            }
            //发布讨论的次数
            int a = new Random().nextInt(3) + 1;
            if(a == 1){
                trainArray.setBbsPostNum("≥5");
            }else if(a == 2){
                trainArray.setBbsPostNum(">0,<5");
            }else{
                trainArray.setBbsPostNum("0");
            }
            //发布讨论的时间
            int a1 = new Random().nextInt(3) + 1;
            if(a1 == 1){
                trainArray.setBbsPostTime("工作时间");
            }else if(a1 == 2){
                trainArray.setBbsPostTime("非工作时间");
            }else{
                trainArray.setBbsPostTime("未知");
            }
            //发布讨论的质量
            int a2 = new Random().nextInt(3) + 1;
            if(a2 == 1){
                trainArray.setBbsPostQuality("低");
            }else if(a2 == 2){
                trainArray.setBbsPostQuality("中");
            }else{
                trainArray.setBbsPostQuality("高");
            }
            //回复讨论的次数
            int a3 = new Random().nextInt(3) + 1;
            if(a3 == 1){
                trainArray.setBbsReplyNum("≥5");
            }else if(a3 == 2){
                trainArray.setBbsReplyNum(">0,<5");
            }else{
                trainArray.setBbsReplyNum("0");
            }
            //回复讨论的时间
            int a4 = new Random().nextInt(3) + 1;
            if(a4 == 1){
                trainArray.setBbsReplyTime("工作时间");
            }else if(a4 == 2){
                trainArray.setBbsReplyTime("非工作时间");
            }else{
                trainArray.setBbsReplyTime("未知");
            }
            //学习课程的总次数
            int a5 = new Random().nextInt(4) + 1;
            if(a5 == 1){
                trainArray.setLearnAllCourseNum("≥10");
            }else if(a5 == 2){
                trainArray.setLearnAllCourseNum("≥5,<10");
            }else if(a5 == 3){
                trainArray.setLearnAllCourseNum(">0,<5");
            }else{
                trainArray.setLearnAllCourseNum("0");
            }
            //学习课程的开始时间
            int a6 = new Random().nextInt(3) + 1;
            if(a6 == 1){
                trainArray.setLearnCourseBeginTime("工作时间");
            }else if(a6 == 2){
                trainArray.setLearnCourseBeginTime("非工作时间");
            }else{
                trainArray.setLearnCourseBeginTime("未知");
            }
            //完成学习课程的比例
            int a7 = new Random().nextInt(3) + 1;
            if(a7 == 1){
                trainArray.setFinishedCourseProportion("低");
            }else if(a7 == 2){
                trainArray.setFinishedCourseProportion("中");
            }else{
                trainArray.setFinishedCourseProportion("高");
            }
            //参与测试的次数
            int a8 = new Random().nextInt(3) + 1;
            if(a8 == 1){
                trainArray.setTestNum("≥5");
            }else if(a8 == 2){
                trainArray.setTestNum(">0,<5");
            }else{
                trainArray.setTestNum("0");
            }
            //参与测试的平均分
            int a9 = new Random().nextInt(4) + 1;
            if(a9 == 1){
                trainArray.setTestScore("≥80");
            }else if(a9 == 2){
                trainArray.setTestScore("≥60,<80");
            }else if(a9 == 3){
                trainArray.setTestScore(">0,<60");
            }else{
                trainArray.setTestScore("0");
            }
            //参与测试的开始时间
            int a10 = new Random().nextInt(3) + 1;
            if(a10 == 1){
                trainArray.setTestBeginTime("工作时间");
            }else if(a10 == 2){
                trainArray.setTestBeginTime("非工作时间");
            }else{
                trainArray.setTestBeginTime("未知");
            }
            //选课数
            int a11 = new Random().nextInt(3) + 1;
            if(a11 == 1){
                trainArray.setChooseCourseNum("≥5");
            }else if(a11 == 2){
                trainArray.setChooseCourseNum(">0,<5");
            }else{
                trainArray.setChooseCourseNum("0");
            }
            //选课分数各部分占的比例
            int a12 = new Random().nextInt(5) + 1;
            if(a12 == 1){
                trainArray.setChooseCoursePartsProportion("学习次数分数所占比例高");
            }else if(a12 == 2){
                trainArray.setChooseCoursePartsProportion("学习时间分数所占比例高");
            }else if(a12 == 3){
                trainArray.setChooseCoursePartsProportion("参与讨论分数所占比例高");
            }else if(a12 == 4){
                trainArray.setChooseCoursePartsProportion("主观评价分数所占比例高");
            }else{
                trainArray.setChooseCoursePartsProportion("未知");
            }
            //用户类型
            int a13 = new Random().nextInt(4) + 1;
            if(a13 == 1){
                trainArray.setUserType("活跃型");
            }else if(a13 == 2){
                trainArray.setUserType("沉思型");
            }else if(a13 == 3){
                trainArray.setUserType("感悟型");
            }else{
                trainArray.setUserType("直觉型");
            }


            //保存
            trainArrayRepository.save(trainArray);
        }
        return "";
    }

    public int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
