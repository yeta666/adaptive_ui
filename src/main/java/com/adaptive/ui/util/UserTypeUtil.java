package com.adaptive.ui.util;

import com.adaptive.ui.domain1.*;
import com.adaptive.ui.domain2.TrainArrayAttributes;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.domain2.UserType;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repository2.UserTypeRepository;
import com.adaptive.ui.service.*;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.type.ModelType;
import com.adaptive.ui.type.UserTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private UserAnswersService userAnswersService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private TrainArrayAttributesService trainArrayAttributesService;

    @Autowired
    private UserTypeService userTypeService;

    /**
     * 根据用户id获取用户数据，并封装成决策树模型能懂的格式
     * @param userId
     * @return
     */
    public String[] getUserData(Integer userId){
        if(userId == null){
            return null;
        }
        //初始化返回数据
        String[] userData = new String[15]; //15个训练集属性

        //获取用户的基本数据
        User user = userService.findOne(userId);
        if(user != null){
            //性别
            if(user.getUserGender() != null){
                userData[0] = user.getUserGender();
            }else{
                logger.info("用户id为" + userId + "的用户数据不合法，没有性别数据！");
                return null;
            }
            //入学时间
            String entranceYear = user.getUserYearOfEntrance();
            if(entranceYear != null && !entranceYear.equals("")){
                if(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(entranceYear.substring(0, 4)) >= 2){
                    userData[1] = "≥2";
                }else{
                    userData[1] = "<2";
                }
            }else{
                logger.info("用户id为" + userId + "的用户数据不合法，没有入学年限数据！");
                return null;
            }
        }else{
            return null;
        }

        //获取用户的发布讨论数据
        List<Bbspost> bbspostList = bbsPostService.findAllByBbpoUserId(userId);
        if(bbspostList != null && bbspostList.size() != 0){
            //发布讨论的次数
            if(bbspostList.size() >= 5){
                userData[2] = "≥5";
            }else{
                userData[2] = ">0,<5";
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
            if(time1 != 0 || time2 != 0){
                if(time1 >= time2){
                    userData[3] = "工作时间";
                }else{
                    userData[3] = "非工作时间";
                }
            }else{
                userData[3] = "未知";
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
                if(bbspost.getBbpoVisitnum() >= 5){
                    visitNum++;
                }
                if(bbspost.getBbpoReplynum() >= 3){
                    replyNum++;
                }
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) >= (1 * Float.parseFloat("1.0") / 3)){
                userData[4] = "中";
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3) ||
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) >= (2 * Float.parseFloat("1.0") / 3)){
                userData[4] = "高";
            }
            if((topNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (bestNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (visitNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3) &&
                    (replyNum * Float.parseFloat("1.0") / bbspostList.size()) < (1 * Float.parseFloat("1.0") / 3)){
                userData[4] = "低";
            }
        }else{
            userData[2] = "0";
            userData[3] = "未知";
            userData[4] = "低";
        }

        //获取用户的回复讨论数据
        List<Bbsreply> bbsreplyList = bbsReplyService.findAllByBbreUserId(userId);
        if(bbsreplyList != null && bbsreplyList.size() != 0){
            //回复讨论的次数
            if(bbspostList.size() >= 5){
                userData[5] = "≥5";
            }else{
                userData[5] = ">0,<5";
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
            if(time1 != 0 || time2 != 0){
                if(time1 >= time2){
                    userData[6] = "工作时间";
                }else{
                    userData[6] = "非工作时间";
                }
            }else{
                userData[6] = "未知";
            }
        }else{
            userData[5] = "0";
            userData[6] = "未知";
        }

        //获取用户的学习课程数据
        List<Learnprocessrecord> learnprocessrecordList = learnProcessRecordService.findAllByBbreUserId(userId);
        if(learnprocessrecordList != null && learnprocessrecordList.size() != 0){
            //学习课程的次数
            if(learnprocessrecordList.size() >= 10){
                userData[7] = "≥10";
            }else if(learnprocessrecordList.size() >= 5 && learnprocessrecordList.size() < 10){
                userData[7] = "≥5,<10";
            }else if(learnprocessrecordList.size() > 0 && learnprocessrecordList.size() < 5){
                userData[7] = ">0,<5";
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
            if(time1 != 0 || time2 != 0){
                if(time1 >= time2){
                    userData[8] = "工作时间";
                }else{
                    userData[8] = "非工作时间";
                }
            }else{
                userData[8] = "未知";
            }
            //完成学习课程的比例
            int endNum = 0;
            for(int i = 0; i < learnprocessrecordList.size(); i++){
                if(learnprocessrecordList.get(i).getLpreEndtime() != null){
                    endNum++;
                }
            }
            double a = endNum * Double.parseDouble("1.0");
            double b = learnprocessrecordList.size() * 2 * Double.parseDouble("1.0") / 3;
            double c = learnprocessrecordList.size() * 1 * Double.parseDouble("1.0") / 3;
            if(endNum != 0){
                if(a >= b){
                    userData[9] = "高";
                }else if(a < b && a >= c){
                    userData[9] = "中";
                }else{
                    userData[9] = "低";
                }
            }else {
                userData[9] = "低";
            }
            System.out.println(a < b && a >= c);
            System.out.println(a + " " + b + " " + c + " " + userData[9]);
        }else{
            userData[7] = "0";
            userData[8] = "未知";
            userData[9] = "低";
        }

        //获取用户的测试数据
        List<ReAutotest> reAutotestList = reAutoTestService.findAllByBbreUserId(userId);
        if(reAutotestList != null && reAutotestList.size() != 0){
            //参与测试的次数
            if(reAutotestList.size() >= 5){
                userData[10] = "≥5";
            }else{
                userData[10] = ">0,<5";
            }
            //参与所有测试的平均分
            float score = 0;
            for(int i = 0; i < reAutotestList.size(); i++){
                score += reAutotestList.get(i).getRateScore();
            }
            if(score != 0){
                if((score / reAutotestList.size()) < 60){
                    userData[11] = ">0,<60";
                }else if((score / reAutotestList.size()) >= 60 && (score / reAutotestList.size()) < 80){
                    userData[11] = "≥60,<80";
                }else if((score / reAutotestList.size()) >= 60){
                    userData[11] = "≥80";
                }
            }else{
                userData[11] = "0";
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
            if(time1 != 0 || time2 != 0){
                if(time1 >= time2){
                    userData[12] = "工作时间";
                }else{
                    userData[12] = "非工作时间";
                }
            }else{
                userData[12] = "未知";
            }
        }else{
            userData[10] = "0";
            userData[11] = "0";
            userData[12] = "未知";
        }

        //获取用户的选课数据
        List<ReSelectcource> reSelectcourceList = reSelectCourceService.findAllByRscoUserId(userId);
        if(reAutotestList != null){
            //选课数
            if(reSelectcourceList.size() >= 5){
                userData[13] = "≥5";
            }else{
                userData[13] = ">0,<5";
            }
            //选课分数中各部分所占比例
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            for(int i = 0; i < reSelectcourceList.size(); i++){
                float p1 = reSelectcourceList.get(i).getRscoLoginscore() / 20;
                float p2 = reSelectcourceList.get(i).getRscoLearntimescore() / 40;
                float p3 = reSelectcourceList.get(i).getRscoBbsdiscussscore() / 20;
                float p4 = reSelectcourceList.get(i).getRscoSubassessscore() / 20;
                if(p1 == 0 && p2 == 0 && p3 == 0 && p4 == 0){
                    continue;
                }else{
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
            }
                if(num1 >= num2 && num1 >= num3 && num1 >= num4){
                    if(num1 != 0) {
                        userData[14] = "学习次数分数所占比例高";
                    }else{
                        userData[14] = "未知";
                    }
                }else if(num2 >= num1 && num2 >= num3 && num2 >= num4){
                    if(num2 != 0) {
                        userData[14] = "学习时间分数所占比例高";
                    }else {
                        userData[14] = "未知";
                    }
                }else if(num3 >= num1 && num3 >= num2 && num3 >= num4){
                    if(num3 != 0) {
                        userData[14] = "参与讨论分数所占比例高";
                    }else{
                        userData[14] = "未知";
                    }
                }else if(num4 >= num1 && num4 >= num2 && num4 >= num3){
                    if(num4 != 0) {
                        userData[14] = "主观评价分数所占比例高";
                    }else{
                        userData[14] = "未知";
                    }
                }
        }else{
            userData[13] = "0";
            userData[14] = "未知";
        }

        return userData;
    }

    /**
     * 设置所有用户类型的方法
     * @param tree
     */
    public void setUserTypes(TreeNode tree){
        //获取所有所有用户
        List<User> userList = userService.findAll();
        if (userList == null || userList.size() == 0) {
            logger.info("训练模型完成之后获取所有用户类型时获取所有用户失败！");
            return;
        }
        //获取训练集属性
        String[] attributesArray = trainArrayAttributesService.getTrainArrayAttributes(ModelType.TYPE1);
        if(attributesArray == null || attributesArray.length == 0){
            logger.info("训练模型完成之后获取所有用户类型时获取训练集属性失败！");
            return;
        }
        for(int i = 0; i < userList.size(); i++){
            //获取一个用户
            User user = userList.get(i);
            //根据id去查询用户是否填写调查表
            if(userAnswersService.findOne(user.getUserId()) == null){
                //获取用户数据
                String[] userData = getUserData(user.getUserId());
                if(userData == null || userData.length == 0){
                    logger.info("训练模型完成之后获取所有用户类型时获取用户" + user.getUserId() + "的用户数据失败！");
                    continue;
                }
                //计算用户类型之后保存
                UserType userType = new UserType();
                userType.setUserId(user.getUserId());
                modelService.getUserTypeByModel(tree, userData, attributesArray);
                userType.setUserType(modelService.getUserType());
                UserType userType1 = userTypeService.save(userType);
                if(userType1 == null){
                    logger.info("训练模型完成之后获取所有用户类型时保存用户" + user.getUserId() + "的用户类型失败！");
                    continue;
                }
            }
        }
    }

    /**
     * 通过用户答案计算用户类型
     * @param answers
     * @return
     */
    public String getUserTypeByUserAnswers(Integer userId, String answers) {

        if(answers == null || answers.equals("") || userId == null || userId.equals("")){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "提交的用户答案或用户id为空！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }

        //转换数据格式
        //List answersList = (List) JSON.parse(answers);
        String[] answersArray = answers.split(",");

        if(answersArray.length != 22){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "提交的用户答案格式不正确！");
            throw new MyException(MessageType.message11 + " code:" + num);
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

        for(int i = 0, j1 = 0, j2 = 1; i < answersArray.length; i++){
            if(i == j1){
                type12List.add(answersArray[i]);
                j1 += 2;
            }else if(i == j2){
                type34List.add(answersArray[i]);
                j2 += 2;
            }
        }

        int[] levelArray = {
                Integer.valueOf(getType(type12List).substring(0, 1)),
                Integer.valueOf(getType(type34List).substring(0, 1))
        };
        String[] typeArray = {
                getType(type12List).substring(1, 2).equals("a") ? UserTypes.TYPE1 : UserTypes.TYPE2,
                getType(type34List).substring(1, 2).equals("a") ? UserTypes.TYPE3 : UserTypes.TYPE4,
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
        UserAnswers userAnswers = new UserAnswers();
        userAnswers.setUserId(userId);
        userAnswers.setAnswers(answers);
        userAnswers.setUserType(typeArray[typeArray.length - 1]);
        UserAnswers userAnswers1 = userAnswersService.save(userAnswers);
        if(userAnswers1 == null){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "保存用户答案失败！");
            throw new MyException(MessageType.message33 + " code:" + num);
        }

        //删除user_type表里面的数据
        if(userTypeService.findOne(userId) != null){
            userTypeService.delete(userId);
            if(userTypeService.findOne(userId) != null){
                logger.info("删除用户在" + userId + "user_type表内的用户类型失败！");
            }
        }

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
