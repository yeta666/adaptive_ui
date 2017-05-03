package com.adaptive.ui.util;

import com.adaptive.ui.domain1.*;
import com.adaptive.ui.domain2.QuestionaryAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.*;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.type.UserType;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * 和用户类型有关的工具类
 * Created by yeta on 2017/4/10/010.
 */
@Component
public class UserTypeUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserTypeUtil.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TreeModelUtil treeModelUtil;

    @Autowired
    private BbsPostService bbsPostService;

    @Autowired
    private BbsReplyService bbsReplyService;

    @Autowired
    private LearnProcessRecordService learnProcessRecordService;

    @Autowired
    private ReAutoTestService reAutoTestService;

    @Autowired
    private ReSelectCourceService reSelectCourceService;

    @Autowired
    private StudentMassedLearningService studentMassedLearningService;

    @Autowired
    private QuestionaryAnswersService questionaryAnswersService;

    /**
     * 通过机器学习方法计算用户类型
     * @param data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String getUserType(String[] data) throws IOException, ClassNotFoundException{
        if(data == null && data.length == 0){
            return "";
        }
        treeModelUtil.getData();
        //获取决策树模型
        TreeNode treeNode = treeModelUtil.getModel();
        if(treeNode == null){
            throw new MyException(MessageType.message6);
        }
        //初始化模型结果
        treeModelUtil.setModelResult();
        //获取给定的数据按照模型走一遍之后的结果
        treeModelUtil.getResult(treeNode, data);
        return treeModelUtil.getModelResult();
    }

    /**
     * 根据用户id获取用户数据，并封装成决策树模型能懂的格式
     * @param userId
     * @return
     */
    public String[] getUserData(Integer userId){
        if(userId == null){
            return new String[0];
        }
        //初始化返回数据
        String[] userData = new String[16];

        //获取用户的数据
        User user = userService.findOne(userId);
        if(user == null){
            for(int i = 0; i < 3; i++){
                userData[i] = "";
            }
        }else{
            //性别
            if(user.getUserGender() != null){
                userData[0] = user.getUserGender();
            }else{
                userData[0] = "";
            }
            //入学时间
            String entranceYear = user.getUserYearOfEntrance();
            if(entranceYear != null && !entranceYear.equals("")){
                if(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(entranceYear.substring(0, 4)) >= 2){
                    userData[1] = "≥2年";
                }else{
                    userData[1] = "<2年";
                }
            }else{
                userData[1] = "";
            }
            //登陆次数
            if(user.getUserLoginnum() != null){
                if(user.getUserLoginnum() >= 10){
                    userData[2] = "≥10次";
                }else{
                    userData[2] = "<10次";
                }
            }else{
                userData[2] = "";
            }
        }

        //获取用户的发布讨论数据
        List<Bbspost> bbspostList = bbsPostService.findAllByBbpoUserId(userId);
        if(bbspostList == null || bbspostList.size() == 0){
            for(int i = 3; i < 6; i++){
                userData[i] = "";
            }
        }else{
            //发布讨论的次数
            if(bbspostList.size() >= 5){
                userData[3] = "≥5次";
            }else{
                userData[3] = "<5次";
            }
            //发布讨论的时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < bbspostList.size(); i++){
                Date postTime = bbspostList.get(i).getBbpoTime();
                if(postTime != null){
                    int hour = postTime.getHours();
                    if((hour >= 8 && hour < 12) || (hour >= 14 && hour < 18)){
                        time1++;
                    }else{
                        time2++;
                    }
                }
            }
            if(time1 >= time2){
                userData[4] = "工作时间多";
            }else{
                userData[4] = "非工作时间多";
            }
            //发布讨论的质量
            int topNum = 0;
            int bestNum = 0;
            int visitNum = 0;
            int replyNum = 0;
            for(int i = 0; i < bbspostList.size(); i++){
                //获取一条数据
                Bbspost bbspost = bbspostList.get(i);
                if(bbspost.getBbpoIstop()){
                    topNum++;
                }
                if(bbspost.getBbpoIsbest()){
                    bestNum++;
                }
                if(bbspost.getBbpoVisitnum() >= 10){
                    visitNum++;
                }
                if(bbspost.getBbpoReplynum() >= 5){
                    replyNum++;
                }
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3)){
                userData[5] = "低";
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3)){
                userData[5] = "中";
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3)){
                userData[5] = "高";
            }
        }

        //获取用户的回复讨论数据
        List<Bbsreply> bbsreplyList = bbsReplyService.findAllByBbreUserId(userId);
        if(bbsreplyList == null || bbsreplyList.size() == 0){
            for(int i = 6; i < 8; i++){
                userData[i] = "";
            }
        }else{
            //回复讨论的次数
            if(bbspostList.size() >= 5){
                userData[6] = "≥5次";
            }else{
                userData[6] = "<5次";
            }
            //回复讨论的时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < bbsreplyList.size(); i++){
                Date replyTime = bbsreplyList.get(i).getBbreTime();
                if(replyTime != null){
                    int hour = replyTime.getHours();
                    if((hour >= 8 && hour < 12) || (hour >= 14 && hour < 18)){
                        time1++;
                    }else{
                        time2++;
                    }
                }
            }
            if(time1 >= time2){
                userData[7] = "工作时间多";
            }else{
                userData[7] = "非工作时间多";
            }
        }

        //获取用户的学习课程数据
        List<Learnprocessrecord> learnprocessrecordList = learnProcessRecordService.findAllByBbreUserId(userId);
        if(learnprocessrecordList == null || learnprocessrecordList.size() == 0){
            for(int i = 8; i < 10; i++){
                userData[i] = "";
            }
        }else{
            //学习课程的次数
            if(learnprocessrecordList.size() >= 10){
                userData[8] = "≥10次";
            }else{
                userData[8] = "<10次";
            }
            //学习课程的开始时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < learnprocessrecordList.size(); i++){
                Date beginTime = learnprocessrecordList.get(i).getLpreBegintime();
                if(beginTime != null){
                    int hour = beginTime.getHours();
                    if((hour >= 8 && hour < 12) || (hour >= 14 && hour < 18)){
                        time1++;
                    }else{
                        time2++;
                    }
                }
            }
            if(time1 >= time2){
                userData[9] = "工作时间多";
            }else{
                userData[9] = "非工作时间多";
            }
        }

        //获取用户的测试数据
        List<ReAutotest> reAutotestList = reAutoTestService.findAllByBbreUserId(userId);
        if(reAutotestList == null || reAutotestList.size() == 0){
            for(int i = 10; i < 13; i++){
                userData[i] = "";
            }
        }else{
            //参与测试的次数
            if(reAutotestList.size() >= 10){
                userData[10] = "≥10次";
            }else{
                userData[10] = "<10次";
            }
            //参与所有测试的平均分
            float score = 0;
            for(int i = 0; i < reAutotestList.size(); i++){
                score += reAutotestList.get(i).getRateScore();
            }
            if((score / reAutotestList.size()) < 60){
                userData[11] = "<60";
            }else if((score / reAutotestList.size()) >= 60 && (score / reAutotestList.size()) < 80){
                userData[11] = "≥60,<80";
            }else if((score / reAutotestList.size()) >= 60){
                userData[11] = "≥80";
            }
            //参与测试的开始时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < reAutotestList.size(); i++){
                Date beginTime = reAutotestList.get(i).getRateStarttime();
                if(beginTime != null){
                    int hour = beginTime.getHours();
                    if((hour >= 8 && hour < 12) || (hour >= 14 && hour < 18)){
                        time1++;
                    }else{
                        time2++;
                    }
                }
            }
            if(time1 >= time2){
                userData[12] = "工作时间多";
            }else{
                userData[12] = "非工作时间多";
            }
        }

        //获取用户的集中学习数据
        List<Studentmassedlearning> studentmassedlearningList = studentMassedLearningService.findAllByBbreUserId(userId);
        if(studentmassedlearningList == null || studentmassedlearningList.size() == 0){
            userData[13] = "";
        }else{
            //参与集中学习的次数
            if(studentmassedlearningList.size() >= 5){
                userData[13] = "≥5次";
            }else{
                userData[13] = "<5次";
            }
        }

        //获取用户的选课数据
        List<ReSelectcource> reSelectcourceList = reSelectCourceService.findAllByRscoUserId(userId);
        if(reAutotestList == null){
            for(int i = 14; i < 16; i++){
                userData[i] = "";
            }
        }else{
            //选课数
            if(reSelectcourceList.size() >= 5){
                userData[14] = "≥5门";
            }else{
                userData[14] = "<5门";
            }
            //选课分数中各部分所占比例
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            for(int i = 0; i < reSelectcourceList.size(); i++){
                float p1 = reSelectcourceList.get(i).getRscoLoginscore() / reSelectcourceList.get(i).getRscoTotalscore();
                float p2 = reSelectcourceList.get(i).getRscoLearntimescore() / reSelectcourceList.get(i).getRscoTotalscore();
                float p3 = reSelectcourceList.get(i).getRscoBbsdiscussscore() / reSelectcourceList.get(i).getRscoTotalscore();
                float p4 = reSelectcourceList.get(i).getRscoSubassessscore() / reSelectcourceList.get(i).getRscoTotalscore();
                if(p1 >= p2 && p1 >= p3 && p1 >= p4){
                    num1++;
                }else if(p2 >= p1 && p2 >= p3 && p2 >= p4){
                    num2++;
                }else if(p3 >= p1 && p3 >= p2 && p3 >= p4){
                    num3++;
                }else if(p4 >= p1 && p4 >= p2 && p4 >= p3){
                    num4++;
                }
            }
            if(num1 >= num2 && num1 >= num3 && num1 >= num4){
                if(num1 != 0) {
                    userData[15] = "学习次数分数所占比例高";
                }else{
                    userData[15] = "";
                }
            }else if(num2 >= num1 && num2 >= num3 && num2 >= num4){
                if(num2 != 0) {
                    userData[15] = "学习时间分数所占比例高";
                }else {
                    userData[15] = "";
                }
            }else if(num3 >= num1 && num3 >= num2 && num3 >= num4){
                if(num3 != 0) {
                    userData[15] = "参与讨论分数所占比例高";
                }else{
                    userData[15] = "";
                }
            }else if(num4 >= num1 && num4 >= num2 && num4 >= num3){
                if(num4 != 0) {
                    userData[15] = "主观评价分数所占比例高";
                }else{
                    userData[15] = "";
                }
            }
        }

        return userData;
    }


    /**
     * 通过调查表计算用户类型
     * @param answers
     * @return
     */
    public String getUserTypeByQuestionary(Integer userId, String answers) {

        if(answers == null || answers.equals("") || userId == null || userId.equals("")){
            logger.info("**************************** " + MessageType.message4);
            throw new MyException(MessageType.message4);
        }

        //转换数据格式
        List answersList = (List) JSON.parse(answers);

        if(answersList.size() != 22){
            logger.info("**************************** " + MessageType.message4);
            throw new MyException(MessageType.message4);
        }

        /**
         * 计算用户类型，计算规则：
         * 题目序号等于数组下标+1
         * 题1、3、5、7、9、11、13、15、17、19、21对应TYPE1和TYPE2
         * 题2、4、6、8、10、12、14、16、18、20、22对应TYPE3和TYPE4
         * 需要统计每两种TYPE的侧重，即每两种TYPE中选a、b的数量之差，最后（较大数—较小数）+ 较大数的字母
         * 例如TYPE1和TYPE2中最后结果为“9a”，则表示属于活跃型的学习风格，且程度很强烈。
         * 1a -- 非常弱、3a -- 较弱、5a -- 一般、7a -- 较强、9a -- 强
         */

        List type12List = new ArrayList();
        List type34List = new ArrayList();

        for(int i = 0, j1 = 0, j2 = 1; i < answersList.size(); i++){
            if(i == j1){
                type12List.add(answersList.get(i));
                j1 += 2;
            }else if(i == j2){
                type34List.add(answersList.get(i));
                j2 += 2;
            }
        }

        int[] levelArray = {
                Integer.valueOf(getType(type12List).substring(0, 1)),
                Integer.valueOf(getType(type34List).substring(0, 1))
        };
        String[] typeArray = {
                getType(type12List).substring(1, 2).equals("a") ? UserType.TYPE1 : UserType.TYPE2,
                getType(type34List).substring(1, 2).equals("a") ? UserType.TYPE3 : UserType.TYPE4,
        };

        //logger.info("levelArray before sort************************* " + Arrays.toString(levelArray));
        //logger.info("typeArray before sort************************* " + Arrays.toString(typeArray));

        //排序，最后结果从小到大
        int temp_level = 0;
        String temp_type = "";
        for(int i = 0; i < 2; i++){
            for(int j = i + 1; j < 2; j++)
                if(levelArray[j] < levelArray[i]){
                    //交换level
                    temp_level = levelArray[j];
                    levelArray[j] = levelArray[i];
                    levelArray[i] = temp_level;
                    //交换type
                    temp_type = typeArray[j];
                    typeArray[j] = typeArray[i];
                    typeArray[i] = temp_type;
                }
        }

        //logger.info("levelArray after sort************************* " + Arrays.toString(levelArray));
        //logger.info("typeArray after sort************************* " + Arrays.toString(typeArray));

        //保存答案和用户类型到数据库
        QuestionaryAnswers questionaryAnswers = new QuestionaryAnswers();
        questionaryAnswers.setUserId(userId);
        questionaryAnswers.setAnswer1((String)answersList.get(0));
        questionaryAnswers.setAnswer2((String)answersList.get(1));
        questionaryAnswers.setAnswer3((String)answersList.get(2));
        questionaryAnswers.setAnswer4((String)answersList.get(3));
        questionaryAnswers.setAnswer5((String)answersList.get(4));
        questionaryAnswers.setAnswer6((String)answersList.get(5));
        questionaryAnswers.setAnswer7((String)answersList.get(6));
        questionaryAnswers.setAnswer8((String)answersList.get(7));
        questionaryAnswers.setAnswer9((String)answersList.get(8));
        questionaryAnswers.setAnswer10((String)answersList.get(9));
        questionaryAnswers.setAnswer11((String)answersList.get(10));
        questionaryAnswers.setAnswer12((String)answersList.get(11));
        questionaryAnswers.setAnswer13((String)answersList.get(12));
        questionaryAnswers.setAnswer14((String)answersList.get(13));
        questionaryAnswers.setAnswer15((String)answersList.get(14));
        questionaryAnswers.setAnswer16((String)answersList.get(15));
        questionaryAnswers.setAnswer17((String)answersList.get(16));
        questionaryAnswers.setAnswer18((String)answersList.get(17));
        questionaryAnswers.setAnswer19((String)answersList.get(18));
        questionaryAnswers.setAnswer20((String)answersList.get(19));
        questionaryAnswers.setAnswer21((String)answersList.get(20));
        questionaryAnswers.setAnswer22((String)answersList.get(21));
        questionaryAnswers.setUserType(typeArray[typeArray.length - 1]);
        questionaryAnswersService.save(questionaryAnswers);

        return typeArray[typeArray.length - 1];
    }

    /**
     * 调查表的计算方法
     */
    public String getType(List list){
        int a_num = 0, b_num = 0;
        String type = "";

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals("a")){
                a_num++;
            }else if(list.get(i).equals("b")){
                b_num++;
            }
        }

        if(a_num > b_num){
            type = String.valueOf(a_num - b_num) + "a";
        }else{
            type = String.valueOf(b_num - a_num) + "b";
        }

        return type;
    }
}
