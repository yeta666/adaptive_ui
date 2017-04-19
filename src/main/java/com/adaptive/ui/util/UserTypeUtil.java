package com.adaptive.ui.util;

import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repositary1.UserRepositary;
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
    private TreeModelUtil treeModelUtil;

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
        String[] data1 = new String[]{"sunny", "hot", "high", "FALSE"};
        String[] data2 = new String[]{"sunny", "hot", "high", "TRUE"};
        String[] data3 = new String[]{"overcast", "hot", "high", "FALSE"};
        String[] data4 = new String[]{"rainy", "mild", "high", "FALSE"};

        return data1;
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
