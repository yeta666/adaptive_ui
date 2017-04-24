package com.adaptive.ui.util;

import com.adaptive.ui.domain1.*;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.*;
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
        //获取决策树模型
        TreeNode treeNode = treeModelUtil.getModel();
        //获取给定的数据按照模型走一边之后的结果
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
        String[] userData = new String[17];

        //获取用户的数据
        User user = userService.findOne(userId);
        if(user == null){
            for(int i = 0; i < 4; i++){
                userData[i] = "";
            }
        }else{
            //性别
            userData[0] = user.getUserGender();
            //入学时间
            String entranceYear = user.getUserYearOfEntrance();
            if(entranceYear != null){
                if(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(entranceYear.substring(0, 4)) >= 2){
                    userData[1] = "≥2年";
                }else{
                    userData[1] = "<2年";
                }
            }else{
                userData[1] = "";
            }
            //教育水平
            userData[2] = user.getUserEduLevel();
            //登陆次数
            if(user.getUserLoginnum() != null && user.getUserLoginnum() >= 10){
                userData[3] = "≥10次";
            }else{
                userData[3] = "<10次";
            }
        }

        //获取用户的发布讨论数据
        List<BbsPost> bbsPostList = bbsPostService.findAllByBbpoUserId(userId);
        if(bbsPostList == null || bbsPostList.size() == 0){
            for(int i = 4; i < 7; i++){
                userData[i] = "";
            }
        }else{
            //发布讨论的次数
            if(bbsPostList.size() >= 5){
                userData[4] = "≥5次";
            }else{
                userData[4] = "<5次";
            }
            //发布讨论的时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < bbsPostList.size(); i++){
                Date postTime = bbsPostList.get(i).getBbpoTime();
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
                userData[5] = "工作时间多";
            }else{
                userData[5] = "非工作时间多";
            }
            //发布讨论的质量
            int topNum = 0;
            int bestNum = 0;
            int visitNum = 0;
            int replyNum = 0;
            for(int i = 0; i < bbsPostList.size(); i++){
                //获取一条数据
                BbsPost bbsPost = bbsPostList.get(i);
                if(bbsPost.getBbpoIstop()){
                    topNum++;
                }
                if(bbsPost.getBbpoIsbest()){
                    bestNum++;
                }
                if(bbsPost.getBbpoVisitnum() >= 10){
                    visitNum++;
                }
                if(bbsPost.getBbpoReplynum() >= 5){
                    replyNum++;
                }
            }
            if((topNum * Float.parseFloat("1.0") / bbsPostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (bestNum * Float.parseFloat("1.0") / bbsPostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (visitNum * Float.parseFloat("1.0") / bbsPostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (replyNum * Float.parseFloat("1.0") / bbsPostList.size()) < (1 * Float.parseFloat("1.0") / 3)){
                userData[6] = "低";
            }
            if((topNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (1 * Float.parseFloat("1.0") / 3)){
                userData[6] = "中";
            }
            if((topNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbsPostList.size()) >= (2 * Float.parseFloat("1.0") / 3)){
                userData[6] = "高";
            }
        }

        //获取用户的回复讨论数据
        List<BbsReply> bbsReplyList = bbsReplyService.findAllByBbreUserId(userId);
        if(bbsReplyList == null || bbsReplyList.size() == 0){
            for(int i = 7; i < 9; i++){
                userData[i] = "";
            }
        }else{
            //回复讨论的次数
            if(bbsPostList.size() >= 5){
                userData[7] = "≥5次";
            }else{
                userData[7] = "<5次";
            }
            //回复讨论的时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < bbsReplyList.size(); i++){
                Date replyTime = bbsReplyList.get(i).getBbreTime();
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
                userData[8] = "工作时间多";
            }else{
                userData[8] = "非工作时间多";
            }
        }

        //获取用户的学习课程数据
        List<LearnProcessRecord> learnProcessRecordList = learnProcessRecordService.findAllByBbreUserId(userId);
        if(learnProcessRecordList == null || learnProcessRecordList.size() == 0){
            for(int i = 9; i < 11; i++){
                userData[i] = "";
            }
        }else{
            //学习课程的次数
            if(learnProcessRecordList.size() >= 10){
                userData[9] = "≥10次";
            }else{
                userData[9] = "<10次";
            }
            //学习课程的开始时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < learnProcessRecordList.size(); i++){
                Date beginTime = learnProcessRecordList.get(i).getLpreBegintime();
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
                userData[10] = "工作时间多";
            }else{
                userData[10] = "非工作时间多";
            }
        }

        //获取用户的测试数据
        List<ReAutoTest> reAutoTestList = reAutoTestService.findAllByBbreUserId(userId);
        if(reAutoTestList == null || reAutoTestList.size() == 0){
            for(int i = 11; i < 14; i++){
                userData[i] = "";
            }
        }else{
            //参与测试的次数
            if(reAutoTestList.size() >= 10){
                userData[11] = "≥10次";
            }else{
                userData[11] = "<10次";
            }
            //参与所有测试的平均分
            float score = 0;
            for(int i = 0; i < reAutoTestList.size(); i++){
                score += reAutoTestList.get(i).getRateScore();
            }
            if((score / reAutoTestList.size()) < 60){
                userData[12] = "<60";
            }else if((score / reAutoTestList.size()) >= 60 && (score / reAutoTestList.size()) < 80){
                userData[12] = "≥60,<80";
            }else if((score / reAutoTestList.size()) >= 60){
                userData[12] = "≥80";
            }
            //参与测试的开始时间
            int time1 = 0;
            int time2 = 0;
            for(int i = 0; i < reAutoTestList.size(); i++){
                Date beginTime = reAutoTestList.get(i).getRateStarttime();
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
                userData[13] = "工作时间多";
            }else{
                userData[13] = "非工作时间多";
            }
        }

        //获取用户的集中学习数据
        List<StudentMassedLearning> studentMassedLearningList = studentMassedLearningService.findAllByBbreUserId(userId);
        if(studentMassedLearningList == null || studentMassedLearningList.size() == 0){
            userData[14] = "";
        }else{
            //参与集中学习的次数
            if(studentMassedLearningList.size() >= 5){
                userData[14] = "≥5次";
            }else{
                userData[14] = "<5次";
            }
        }

        //获取用户的选课数据
        List<ReSelectCource> reSelectCourceList = reSelectCourceService.findAllByRscoUserId(userId);
        if(reAutoTestList == null){
            for(int i = 15; i < 17; i++){
                userData[i] = "";
            }
        }else{
            //选课数
            if(reSelectCourceList.size() >= 5){
                userData[15] = "≥5门";
            }else{
                userData[15] = "<5门";
            }
            //选课分数中各部分所占比例
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            for(int i = 0; i < reSelectCourceList.size(); i++){
                float p1 = reSelectCourceList.get(i).getRscoLoginscore() / reSelectCourceList.get(i).getRscoTotalscore();
                float p2 = reSelectCourceList.get(i).getRscoLearntimescore() / reSelectCourceList.get(i).getRscoTotalscore();
                float p3 = reSelectCourceList.get(i).getRscoBbsdiscussscore() / reSelectCourceList.get(i).getRscoTotalscore();
                float p4 = reSelectCourceList.get(i).getRscoSubassessscore() / reSelectCourceList.get(i).getRscoTotalscore();
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
                userData[16] = "学习次数分数占的比例高";
            }else if(num2 >= num1 && num2 >= num3 && num2 >= num4){
                userData[16] = "学习时间分数占的比例高";
            }else if(num3 >= num1 && num3 >= num2 && num3 >= num4){
                userData[16] = "参与讨论分数占的比例高";
            }else if(num4 >= num1 && num4 >= num2 && num4 >= num3){
                userData[16] = "主观评价分数占的比例高";
            }
        }

        return userData;
    }


    /**
     * 通过调查表计算用户类型
     * @param answers
     * @return
     */
    public String getUserTypeByQuestionary(String answers) {

        if(answers == null || answers.equals("")){
            return null;
        }

        //转换数据格式
        List answersList = (List) JSON.parse(answers);

        /**
         * 计算用户类型，计算规则：
         * 题目序号等于数组下标+1
         * 题1、5、9、13、17、21、25、29、33、37、41对应TYPE1和TYPE2
         * 题2、6、10、14、18、22、26、30、34、38、42对应TYPE3和TYPE4
         * 题3、7、11、15、19、23、27、31、35、39、43对应TYPE4和TYPE6
         * 题4、8、12、16、20、24、28、32、36、40、44对应TYPE7和TYPE8
         * 需要统计每两种TYPE的侧重，即每两种TYPE中选a、b的数量之差，最后（较大数—较小数）+ 较大数的字母
         * 例如TYPE1和TYPE2中最后结果为“9a”，则表示属于活跃型的学习风格，且程度很强烈。
         * 1a -- 非常弱、3a -- 较弱、5a -- 一般、7a -- 较强、9a -- 强
         */

        List type12List = new ArrayList();
        List type34List = new ArrayList();
        List type56List = new ArrayList();
        List type78List = new ArrayList();

        for(int i = 0, j1 = 0, j2 = 1, j3 = 2, j4 = 3; i < answersList.size(); i++){
            if(i == j1){
                type12List.add(answersList.get(i));
                j1 += 4;
            }else if(i == j2){
                type34List.add(answersList.get(i));
                j2 += 4;
            }else if(i == j3){
                type56List.add(answersList.get(i));
                j3 += 4;
            }else if(i == j4){
                type78List.add(answersList.get(i));
                j4 += 4;
            }
        }

        int[] levelArray = {
                Integer.valueOf(getType(type12List).substring(0, 1)),
                Integer.valueOf(getType(type34List).substring(0, 1)),
                Integer.valueOf(getType(type56List).substring(0, 1)),
                Integer.valueOf(getType(type78List).substring(0, 1))
        };
        String[] typeArray = {
                getType(type12List).substring(1, 2).equals("a") ? UserType.TYPE1 : UserType.TYPE2,
                getType(type34List).substring(1, 2).equals("a") ? UserType.TYPE3 : UserType.TYPE4,
                getType(type56List).substring(1, 2).equals("a") ? UserType.TYPE5 : UserType.TYPE6,
                getType(type78List).substring(1, 2).equals("a") ? UserType.TYPE7 : UserType.TYPE8
        };

        logger.info("levelArray before sort************************* " + Arrays.toString(levelArray));
        logger.info("typeArray before sort************************* " + Arrays.toString(typeArray));

        //排序，最后结果从小到大
        int temp_level = 0;
        String temp_type = "";
        for(int i = 0; i < 4; i++){
            for(int j = i + 1; j < 4; j++)
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

        logger.info("levelArray after sort************************* " + Arrays.toString(levelArray));
        logger.info("typeArray after sort************************* " + Arrays.toString(typeArray));

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
