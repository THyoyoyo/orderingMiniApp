package com.orderingMinAppAip.controller;


import com.orderingMinAppAip.vo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/capture")
@Api(tags = "抓取数据工具")
@CrossOrigin(origins = "*")
public class CaptureController {

    @Autowired
    private OkHttpClient okHttpClient;



    @GetMapping("/getHowToCook")
    @ApiOperation("抓取总菜单")
    public R getHowToCook() throws IOException {
       Request request = new Request.Builder()
               .url("https://raw.githubusercontent.com/Anduin2017/HowToCook/master/README.md")
               .method("GET",null).build();

//       Response response = okHttpClient.newCall(request).execute();
       String html = html();
       html =  removePrefix(html,"## 做菜之前");
       html = html.replace("\n",",").replace("###",",").replace("-",",");
       String[] htmlArr = html.split(",");
        ArrayList<Object> objects = new ArrayList<>();
        for (String item : htmlArr) {
            if(item == ""){
                continue;
            }
            item = item.replace(" ","");
            String pattern = "\\[(.*?)\\]\\((.*?)\\)";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(item);
            if (matcher.matches()) {
                String bracketContent = matcher.group(1);
                String parenthesisContent = matcher.group(2);
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",bracketContent);
                map.put("infoPath","https://raw.githubusercontent.com/Anduin2017/HowToCook/master"+parenthesisContent.substring(1));
                map.put("basePath","https://raw.githubusercontent.com/Anduin2017/HowToCook/master"+getPrefix(parenthesisContent.substring(1),bracketContent+".md") );
                objects.add(map);
            }
        }
       return R.succeed(objects);
   }


   @GetMapping("/getHowToCookByUrl")
   @ApiOperation("抓取菜品详情")
   public R getHowToCookByUrl(@RequestParam("infoPath") String infoPath,@RequestParam("basePath") String basePath) throws IOException {

       Request request = new Request.Builder()
               .url(infoPath)
               .method("GET",null).build();
      Response response = okHttpClient.newCall(request).execute();
       String info = response.body().string();
       // 定义正则表达式
       String regex = "!\\[(.*?)\\]\\((.*?)\\)";
       // 创建 Pattern 对象
       Pattern pattern = Pattern.compile(regex);
       // 创建 Matcher 对象
       Matcher matcher = pattern.matcher(info);

       // 使用 Matcher 进行匹配和替换
       StringBuffer outputStr = new StringBuffer();
       while (matcher.find()) {
           String altText = matcher.group(1); // 获取匹配到的文本
           String imagePath = matcher.group(2); // 获取匹配到的文件路径
           imagePath =basePath + imagePath.substring(2);
           String replacement = "!["+altText+"]"+"("+imagePath+")";
           matcher.appendReplacement(outputStr, replacement);
       }
       matcher.appendTail(outputStr);
       return R.succeed(outputStr.toString());
   }

    public static String removePrefix(String input, String prefix) {
        int index = input.indexOf(prefix);
        if (index != -1) {
            return input.substring(index).trim();
        } else {
            return input;
        }
    }

    public static String getPrefix(String input, String prefix) {
        int index = input.indexOf(prefix);
        if (index != -1) {
            return input.substring(0,index).trim();
        } else {
            return input;
        }
    }

    public static String html(){
        return "# 程序员做饭指南\n" +
                "\n" +
                "[![build](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml/badge.svg)](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml)\n" +
                "[![License](https://img.shields.io/github/license/Anduin2017/HowToCook)](./LICENSE)\n" +
                "[![GitHub contributors](https://img.shields.io/github/contributors/Anduin2017/HowToCook)](https://github.com/Anduin2017/HowToCook/graphs/contributors)\n" +
                "[![npm](https://img.shields.io/npm/v/how-to-cook)](https://www.npmjs.com/package/how-to-cook)\n" +
                "\n" +
                "最近在家隔离，出不了门。只能宅在家做饭了。作为程序员，我偶尔在网上找找菜谱和做法。但是这些菜谱往往写法千奇百怪，经常中间莫名出来一些材料。对于习惯了形式语言的程序员来说极其不友好。\n" +
                "\n" +
                "所以，我计划自己搜寻菜谱并结合实际做菜的经验，准备用更清晰精准的描述来整理常见菜的做法，以方便程序员在家做饭。\n" +
                "\n" +
                "同样，我希望它是一个由社区驱动和维护的开源项目，使更多人能够一起做一个有趣的仓库。所以非常欢迎大家贡献它~\n" +
                "\n" +
                "## 如何贡献\n" +
                "\n" +
                "针对发现的问题，直接修改并提交 Pull request 即可。\n" +
                "\n" +
                "在写新菜谱时，请复制并修改已有的菜谱模板: [示例菜](https://github.com/Anduin2017/HowToCook/blob/master/dishes/template/%E7%A4%BA%E4%BE%8B%E8%8F%9C/%E7%A4%BA%E4%BE%8B%E8%8F%9C.md?plain=1)。\n" +
                "\n" +
                "## 做菜之前\n" +
                "\n" +
                "- [厨房准备](./tips/厨房准备.md)\n" +
                "- [如何选择现在吃什么](./tips/如何选择现在吃什么.md)\n" +
                "- [高压力锅](./tips/learn/高压力锅.md)\n" +
                "- [去腥](./tips/learn/去腥.md)\n" +
                "- [食品安全](./tips/learn/食品安全.md)\n" +
                "- [微波炉](./tips/learn/微波炉.md)\n" +
                "- [学习焯水](./tips/learn/学习焯水.md)\n" +
                "- [学习炒与煎](./tips/learn/学习炒与煎.md)\n" +
                "- [学习凉拌](./tips/learn/学习凉拌.md)\n" +
                "- [学习腌](./tips/learn/学习腌.md)\n" +
                "- [学习蒸](./tips/learn/学习蒸.md)\n" +
                "- [学习煮](./tips/learn/学习煮.md)\n" +
                "\n" +
                "## 菜谱\n" +
                "\n" +
                "### 家常菜\n" +
                "\n" +
                "### 素菜\n" +
                "\n" +
                "- [拔丝土豆](./dishes/vegetable_dish/拔丝土豆/拔丝土豆.md)\n" +
                "- [白灼菜心](./dishes/vegetable_dish/白灼菜心/白灼菜心.md)\n" +
                "- [包菜炒鸡蛋粉丝](./dishes/vegetable_dish/包菜炒鸡蛋粉丝/包菜炒鸡蛋粉丝.md)\n" +
                "- [菠菜炒鸡蛋](./dishes/vegetable_dish/菠菜炒鸡蛋/菠菜炒鸡蛋.md)\n" +
                "- [炒滑蛋](./dishes/vegetable_dish/炒滑蛋/炒滑蛋.md)\n" +
                "- [炒茄子](./dishes/vegetable_dish/炒茄子.md)\n" +
                "- [炒青菜](./dishes/vegetable_dish/炒青菜.md)\n" +
                "- [葱煎豆腐](./dishes/vegetable_dish/葱煎豆腐.md)\n" +
                "- [脆皮豆腐](./dishes/vegetable_dish/脆皮豆腐.md)\n" +
                "- [地三鲜](./dishes/vegetable_dish/地三鲜.md)\n" +
                "- [干锅花菜](./dishes/vegetable_dish/干锅花菜/干锅花菜.md)\n" +
                "- [蚝油三鲜菇](./dishes/vegetable_dish/蚝油三鲜菇/蚝油三鲜菇.md)\n" +
                "- [蚝油生菜](./dishes/vegetable_dish/蚝油生菜.md)\n" +
                "- [荷兰豆炒腊肠](./dishes/vegetable_dish/荷兰豆炒腊肠/荷兰豆炒腊肠.md)\n" +
                "- [红烧冬瓜](./dishes/vegetable_dish/红烧冬瓜/红烧冬瓜.md)\n" +
                "- [红烧茄子](./dishes/vegetable_dish/红烧茄子.md)\n" +
                "- [虎皮青椒](./dishes/vegetable_dish/虎皮青椒/虎皮青椒.md)\n" +
                "- [话梅煮毛豆](./dishes/vegetable_dish/话梅煮毛豆/话梅煮毛豆.md)\n" +
                "- [鸡蛋羹](./dishes/vegetable_dish/鸡蛋羹/鸡蛋羹.md)\n" +
                "- [微波炉鸡蛋羹](./dishes/vegetable_dish/鸡蛋羹/微波炉鸡蛋羹.md)\n" +
                "- [蒸箱鸡蛋羹](./dishes/vegetable_dish/鸡蛋羹/蒸箱鸡蛋羹.md)\n" +
                "- [鸡蛋火腿炒黄瓜](./dishes/vegetable_dish/鸡蛋火腿炒黄瓜.md)\n" +
                "- [茄子炖土豆](./dishes/vegetable_dish/茄子炖土豆.md)\n" +
                "- [茭白炒肉](./dishes/vegetable_dish/茭白炒肉/茭白炒肉.md)\n" +
                "- [椒盐玉米](./dishes/vegetable_dish/椒盐玉米/椒盐玉米.md)\n" +
                "- [金针菇日本豆腐煲](./dishes/vegetable_dish/金针菇日本豆腐煲.md)\n" +
                "- [烤茄子](./dishes/vegetable_dish/烤茄子/烤茄子.md)\n" +
                "- [榄菜肉末四季豆](./dishes/vegetable_dish/榄菜肉末四季豆/榄菜肉末四季豆.md)\n" +
                "- [雷椒皮蛋](./dishes/vegetable_dish/雷椒皮蛋.md)\n" +
                "- [凉拌黄瓜](./dishes/vegetable_dish/凉拌黄瓜.md)\n" +
                "- [凉拌木耳](./dishes/vegetable_dish/凉拌木耳/凉拌木耳.md)\n" +
                "- [凉拌莴笋](./dishes/vegetable_dish/凉拌莴笋/凉拌莴笋.md)\n" +
                "- [凉拌油麦菜](./dishes/vegetable_dish/凉拌油麦菜.md)\n" +
                "- [麻婆豆腐](./dishes/vegetable_dish/麻婆豆腐/麻婆豆腐.md)\n" +
                "- [蒲烧茄子](./dishes/vegetable_dish/蒲烧茄子.md)\n" +
                "- [芹菜拌茶树菇](./dishes/vegetable_dish/芹菜拌茶树菇/芹菜拌茶树菇.md)\n" +
                "- [陕北熬豆角](./dishes/vegetable_dish/陕北熬豆角.md)\n" +
                "- [上汤娃娃菜](./dishes/vegetable_dish/上汤娃娃菜/上汤娃娃菜.md)\n" +
                "- [手撕包菜](./dishes/vegetable_dish/手撕包菜/手撕包菜.md)\n" +
                "- [水油焖蔬菜](./dishes/vegetable_dish/水油焖蔬菜.md)\n" +
                "- [素炒豆角](./dishes/vegetable_dish/素炒豆角.md)\n" +
                "- [酸辣土豆丝](./dishes/vegetable_dish/酸辣土豆丝.md)\n" +
                "- [糖拌西红柿](./dishes/vegetable_dish/糖拌西红柿/糖拌西红柿.md)\n" +
                "- [莴笋叶煎饼](./dishes/vegetable_dish/莴笋叶煎饼/莴笋叶煎饼.md)\n" +
                "- [西红柿炒鸡蛋](./dishes/vegetable_dish/西红柿炒鸡蛋.md)\n" +
                "- [西红柿豆腐汤羹](./dishes/vegetable_dish/西红柿豆腐汤羹/西红柿豆腐汤羹.md)\n" +
                "- [西葫芦炒鸡蛋](./dishes/vegetable_dish/西葫芦炒鸡蛋/西葫芦炒鸡蛋.md)\n" +
                "- [小炒藕丁](./dishes/vegetable_dish/小炒藕丁/小炒藕丁.md)\n" +
                "- [洋葱炒鸡蛋](./dishes/vegetable_dish/洋葱炒鸡蛋/洋葱炒鸡蛋.md)\n" +
                "\n" +
                "### 荤菜\n" +
                "\n" +
                "- [白菜猪肉炖粉条](./dishes/meat_dish/白菜猪肉炖粉条.md)\n" +
                "- [带把肘子](./dishes/meat_dish/带把肘子.md)\n" +
                "- [冬瓜酿肉](./dishes/meat_dish/冬瓜酿肉/冬瓜酿肉.md)\n" +
                "- [番茄红酱](./dishes/meat_dish/番茄红酱.md)\n" +
                "- [干煸仔鸡](./dishes/meat_dish/干煸仔鸡/干煸仔鸡.md)\n" +
                "- [宫保鸡丁](./dishes/meat_dish/宫保鸡丁/宫保鸡丁.md)\n" +
                "- [咕噜肉](./dishes/meat_dish/咕噜肉.md)\n" +
                "- [黑椒牛柳](./dishes/meat_dish/黑椒牛柳/黑椒牛柳.md)\n" +
                "- [简易红烧肉](./dishes/meat_dish/红烧肉/简易红烧肉.md)\n" +
                "- [南派红烧肉](./dishes/meat_dish/红烧肉/南派红烧肉.md)\n" +
                "- [红烧猪蹄](./dishes/meat_dish/红烧猪蹄/红烧猪蹄.md)\n" +
                "- [湖南家常红烧肉](./dishes/meat_dish/湖南家常红烧肉/湖南家常红烧肉.md)\n" +
                "- [黄瓜炒肉](./dishes/meat_dish/黄瓜炒肉.md)\n" +
                "- [黄焖鸡](./dishes/meat_dish/黄焖鸡.md)\n" +
                "- [徽派红烧肉](./dishes/meat_dish/徽派红烧肉/徽派红烧肉.md)\n" +
                "- [回锅肉](./dishes/meat_dish/回锅肉/回锅肉.md)\n" +
                "- [尖椒炒牛肉](./dishes/meat_dish/尖椒炒牛肉.md)\n" +
                "- [姜炒鸡](./dishes/meat_dish/姜炒鸡/姜炒鸡.md)\n" +
                "- [姜葱捞鸡](./dishes/meat_dish/姜葱捞鸡/姜葱捞鸡.md)\n" +
                "- [酱牛肉](./dishes/meat_dish/酱牛肉/酱牛肉.md)\n" +
                "- [酱排骨](./dishes/meat_dish/酱排骨/酱排骨.md)\n" +
                "- [椒盐排条](./dishes/meat_dish/椒盐排条.md)\n" +
                "- [咖喱肥牛](./dishes/meat_dish/咖喱肥牛/咖喱肥牛.md)\n" +
                "- [烤鸡翅](./dishes/meat_dish/烤鸡翅.md)\n" +
                "- [可乐鸡翅](./dishes/meat_dish/可乐鸡翅.md)\n" +
                "- [口水鸡](./dishes/meat_dish/口水鸡/口水鸡.md)\n" +
                "- [辣椒炒肉](./dishes/meat_dish/辣椒炒肉.md)\n" +
                "- [老式锅包肉](./dishes/meat_dish/老式锅包肉/老式锅包肉.md)\n" +
                "- [冷吃兔](./dishes/meat_dish/冷吃兔.md)\n" +
                "- [荔枝肉](./dishes/meat_dish/荔枝肉/荔枝肉.md)\n" +
                "- [凉拌鸡丝](./dishes/meat_dish/凉拌鸡丝/凉拌鸡丝.md)\n" +
                "- [萝卜炖羊排](./dishes/meat_dish/萝卜炖羊排.md)\n" +
                "- [麻辣香锅](./dishes/meat_dish/麻辣香锅.md)\n" +
                "- [麻婆豆腐](./dishes/meat_dish/麻婆豆腐/麻婆豆腐.md)\n" +
                "- [梅菜扣肉](./dishes/meat_dish/梅菜扣肉/梅菜扣肉.md)\n" +
                "- [啤酒鸭](./dishes/meat_dish/啤酒鸭/啤酒鸭.md)\n" +
                "- [青椒土豆炒肉](./dishes/meat_dish/青椒土豆炒肉/青椒土豆炒肉.md)\n" +
                "- [肉饼炖蛋](./dishes/meat_dish/肉饼炖蛋.md)\n" +
                "- [杀猪菜](./dishes/meat_dish/杀猪菜.md)\n" +
                "- [山西过油肉](./dishes/meat_dish/山西过油肉.md)\n" +
                "- [商芝肉](./dishes/meat_dish/商芝肉.md)\n" +
                "- [瘦肉土豆片](./dishes/meat_dish/瘦肉土豆片/瘦肉土豆片.md)\n" +
                "- [水煮牛肉](./dishes/meat_dish/水煮牛肉/水煮牛肉.md)\n" +
                "- [水煮肉片](./dishes/meat_dish/水煮肉片.md)\n" +
                "- [蒜苔炒肉末](./dishes/meat_dish/蒜苔炒肉末.md)\n" +
                "- [台式卤肉饭](./dishes/meat_dish/台式卤肉饭/台式卤肉饭.md)\n" +
                "- [糖醋里脊](./dishes/meat_dish/糖醋里脊.md)\n" +
                "- [糖醋排骨](./dishes/meat_dish/糖醋排骨/糖醋排骨.md)\n" +
                "- [土豆炖排骨](./dishes/meat_dish/土豆炖排骨/土豆炖排骨.md)\n" +
                "- [无骨鸡爪](./dishes/meat_dish/无骨鸡爪/无骨鸡爪.md)\n" +
                "- [西红柿牛腩](./dishes/meat_dish/西红柿牛腩/西红柿牛腩.md)\n" +
                "- [西红柿土豆炖牛肉](./dishes/meat_dish/西红柿土豆炖牛肉/西红柿土豆炖牛肉.md)\n" +
                "- [乡村啤酒鸭](./dishes/meat_dish/乡村啤酒鸭.md)\n" +
                "- [香干芹菜炒肉](./dishes/meat_dish/香干芹菜炒肉/香干芹菜炒肉.md)\n" +
                "- [香干肉丝](./dishes/meat_dish/香干肉丝.md)\n" +
                "- [香菇滑鸡](./dishes/meat_dish/香菇滑鸡/香菇滑鸡.md)\n" +
                "- [香煎五花肉](./dishes/meat_dish/香煎五花肉/香煎五花肉.md)\n" +
                "- [小炒黄牛肉](./dishes/meat_dish/小炒黄牛肉/小炒黄牛肉.md)\n" +
                "- [小炒鸡肝](./dishes/meat_dish/小炒鸡肝/小炒鸡肝.md)\n" +
                "- [小炒肉](./dishes/meat_dish/小炒肉.md)\n" +
                "- [新疆大盘鸡](./dishes/meat_dish/新疆大盘鸡/新疆大盘鸡.md)\n" +
                "- [血浆鸭](./dishes/meat_dish/血浆鸭/血浆鸭.md)\n" +
                "- [羊排焖面](./dishes/meat_dish/羊排焖面/羊排焖面.md)\n" +
                "- [洋葱炒猪肉](./dishes/meat_dish/洋葱炒猪肉.md)\n" +
                "- [意式烤鸡](./dishes/meat_dish/意式烤鸡.md)\n" +
                "- [鱼香茄子](./dishes/meat_dish/鱼香茄子/鱼香茄子.md)\n" +
                "- [鱼香肉丝](./dishes/meat_dish/鱼香肉丝.md)\n" +
                "- [猪皮冻](./dishes/meat_dish/猪皮冻/猪皮冻.md)\n" +
                "- [猪肉烩酸菜](./dishes/meat_dish/猪肉烩酸菜.md)\n" +
                "- [柱候牛腩](./dishes/meat_dish/柱候牛腩/柱候牛腩.md)\n" +
                "- [孜然牛肉](./dishes/meat_dish/孜然牛肉.md)\n" +
                "- [醉排骨](./dishes/meat_dish/醉排骨/醉排骨.md)\n" +
                "\n" +
                "### 水产\n" +
                "\n" +
                "- [白灼虾](./dishes/aquatic/白灼虾/白灼虾.md)\n" +
                "- [鳊鱼炖豆腐](./dishes/aquatic/鳊鱼炖豆腐/鳊鱼炖豆腐.md)\n" +
                "- [蛏抱蛋](./dishes/aquatic/蛏抱蛋/蛏抱蛋.md)\n" +
                "- [葱烧海参](./dishes/aquatic/葱烧海参/葱烧海参.md)\n" +
                "- [葱油桂鱼](./dishes/aquatic/葱油桂鱼/葱油桂鱼.md)\n" +
                "- [干煎阿根廷红虾](./dishes/aquatic/干煎阿根廷红虾/干煎阿根廷红虾.md)\n" +
                "- [红烧鲤鱼](./dishes/aquatic/红烧鲤鱼.md)\n" +
                "- [红烧鱼](./dishes/aquatic/红烧鱼.md)\n" +
                "- [红烧鱼头](./dishes/aquatic/红烧鱼头.md)\n" +
                "- [黄油煎虾](./dishes/aquatic/黄油煎虾/黄油煎虾.md)\n" +
                "- [烤鱼](./dishes/aquatic/混合烤鱼/烤鱼.md)\n" +
                "- [咖喱炒蟹](./dishes/aquatic/咖喱炒蟹.md)\n" +
                "- [鲤鱼炖白菜](./dishes/aquatic/鲤鱼炖白菜/鲤鱼炖白菜.md)\n" +
                "- [清蒸鲈鱼](./dishes/aquatic/清蒸鲈鱼/清蒸鲈鱼.md)\n" +
                "- [清蒸生蚝](./dishes/aquatic/清蒸生蚝.md)\n" +
                "- [水煮鱼](./dishes/aquatic/水煮鱼.md)\n" +
                "- [蒜蓉虾](./dishes/aquatic/蒜蓉虾/蒜蓉虾.md)\n" +
                "- [糖醋鲤鱼](./dishes/aquatic/糖醋鲤鱼/糖醋鲤鱼.md)\n" +
                "- [微波葱姜黑鳕鱼](./dishes/aquatic/微波葱姜黑鳕鱼.md)\n" +
                "- [香煎翘嘴鱼](./dishes/aquatic/香煎翘嘴鱼/香煎翘嘴鱼.md)\n" +
                "- [小龙虾](./dishes/aquatic/小龙虾/小龙虾.md)\n" +
                "- [油焖大虾](./dishes/aquatic/油焖大虾/油焖大虾.md)\n" +
                "\n" +
                "### 早餐\n" +
                "\n" +
                "- [茶叶蛋](./dishes/breakfast/茶叶蛋.md)\n" +
                "- [蛋煎糍粑](./dishes/breakfast/蛋煎糍粑.md)\n" +
                "- [桂圆红枣粥](./dishes/breakfast/桂圆红枣粥.md)\n" +
                "- [鸡蛋三明治](./dishes/breakfast/鸡蛋三明治.md)\n" +
                "- [煎饺](./dishes/breakfast/煎饺.md)\n" +
                "- [金枪鱼酱三明治](./dishes/breakfast/金枪鱼酱三明治.md)\n" +
                "- [空气炸锅面包片](./dishes/breakfast/空气炸锅面包片.md)\n" +
                "- [美式炒蛋](./dishes/breakfast/美式炒蛋.md)\n" +
                "- [牛奶燕麦](./dishes/breakfast/牛奶燕麦.md)\n" +
                "- [水煮玉米](./dishes/breakfast/水煮玉米.md)\n" +
                "- [苏格兰蛋](./dishes/breakfast/苏格兰蛋/苏格兰蛋.md)\n" +
                "- [太阳蛋](./dishes/breakfast/太阳蛋.md)\n" +
                "- [溏心蛋](./dishes/breakfast/溏心蛋.md)\n" +
                "- [吐司果酱](./dishes/breakfast/吐司果酱.md)\n" +
                "- [微波炉蛋糕](./dishes/breakfast/微波炉蛋糕.md)\n" +
                "- [燕麦鸡蛋饼](./dishes/breakfast/燕麦鸡蛋饼.md)\n" +
                "- [蒸花卷](./dishes/breakfast/蒸花卷.md)\n" +
                "- [蒸水蛋](./dishes/breakfast/蒸水蛋.md)\n" +
                "\n" +
                "### 主食\n" +
                "\n" +
                "- [炒方便面](./dishes/staple/炒方便面.md)\n" +
                "- [炒河粉](./dishes/staple/炒河粉.md)\n" +
                "- [炒凉粉](./dishes/staple/炒凉粉/炒凉粉.md)\n" +
                "- [炒馍](./dishes/staple/炒馍.md)\n" +
                "- [炒年糕](./dishes/staple/炒年糕.md)\n" +
                "- [炒意大利面](./dishes/staple/炒意大利面/炒意大利面.md)\n" +
                "- [蛋炒饭](./dishes/staple/蛋炒饭.md)\n" +
                "- [豆角焖面](./dishes/staple/豆角焖面/豆角焖面.md)\n" +
                "- [韩式拌饭](./dishes/staple/韩式拌饭/韩式拌饭.md)\n" +
                "- [河南蒸面条](./dishes/staple/河南蒸面条/河南蒸面条.md)\n" +
                "- [火腿饭团](./dishes/staple/火腿饭团/火腿饭团.md)\n" +
                "- [基础牛奶面包](./dishes/staple/基础牛奶面包/基础牛奶面包.md)\n" +
                "- [茄子肉煎饼](./dishes/staple/茄子肉煎饼/茄子肉煎饼.md)\n" +
                "- [鲣鱼海苔玉米饭](./dishes/staple/鲣鱼海苔玉米饭/鲣鱼海苔玉米饭.md)\n" +
                "- [酱拌荞麦面](./dishes/staple/酱拌荞麦面/酱拌荞麦面.md)\n" +
                "- [空气炸锅照烧鸡饭](./dishes/staple/空气炸锅照烧鸡饭/空气炸锅照烧鸡饭.md)\n" +
                "- [醪糟小汤圆](./dishes/staple/醪糟小汤圆.md)\n" +
                "- [老干妈拌面](./dishes/staple/老干妈拌面.md)\n" +
                "- [老友猪肉粉](./dishes/staple/老友猪肉粉/老友猪肉粉.md)\n" +
                "- [烙饼](./dishes/staple/烙饼/烙饼.md)\n" +
                "- [凉粉](./dishes/staple/凉粉/凉粉.md)\n" +
                "- [麻辣减脂荞麦面](./dishes/staple/麻辣减脂荞麦面.md)\n" +
                "- [麻油拌面](./dishes/staple/麻油拌面.md)\n" +
                "- [电饭煲蒸米饭](./dishes/staple/米饭/电饭煲蒸米饭.md)\n" +
                "- [煮锅蒸米饭](./dishes/staple/米饭/煮锅蒸米饭.md)\n" +
                "- [披萨饼皮](./dishes/staple/披萨饼皮/披萨饼皮.md)\n" +
                "- [热干面](./dishes/staple/热干面.md)\n" +
                "- [日式咖喱饭](./dishes/staple/日式咖喱饭/日式咖喱饭.md)\n" +
                "- [芝麻烧饼](./dishes/staple/烧饼/芝麻烧饼.md)\n" +
                "- [手工水饺](./dishes/staple/手工水饺.md)\n" +
                "- [酸辣蕨根粉](./dishes/staple/酸辣蕨根粉.md)\n" +
                "- [汤面](./dishes/staple/汤面.md)\n" +
                "- [微波炉腊肠煲仔饭](./dishes/staple/微波炉腊肠煲仔饭/微波炉腊肠煲仔饭.md)\n" +
                "- [西红柿鸡蛋挂面](./dishes/staple/西红柿鸡蛋挂面/西红柿鸡蛋挂面.md)\n" +
                "- [扬州炒饭](./dishes/staple/扬州炒饭/扬州炒饭.md)\n" +
                "- [炸酱面](./dishes/staple/炸酱面.md)\n" +
                "- [蒸卤面](./dishes/staple/蒸卤面.md)\n" +
                "- [中式馅饼](./dishes/staple/中式馅饼/中式馅饼.md)\n" +
                "- [煮泡面加蛋](./dishes/staple/煮泡面加蛋.md)\n" +
                "\n" +
                "### 半成品加工\n" +
                "\n" +
                "- [半成品意面](./dishes/semi-finished/半成品意面.md)\n" +
                "- [空气炸锅鸡翅中](./dishes/semi-finished/空气炸锅鸡翅中/空气炸锅鸡翅中.md)\n" +
                "- [空气炸锅羊排](./dishes/semi-finished/空气炸锅羊排/空气炸锅羊排.md)\n" +
                "- [懒人蛋挞](./dishes/semi-finished/懒人蛋挞/懒人蛋挞.md)\n" +
                "- [凉皮](./dishes/semi-finished/凉皮.md)\n" +
                "- [牛油火锅底料](./dishes/semi-finished/牛油火锅底料.md)\n" +
                "- [速冻馄饨](./dishes/semi-finished/速冻馄饨.md)\n" +
                "- [速冻水饺](./dishes/semi-finished/速冻水饺.md)\n" +
                "- [速冻汤圆](./dishes/semi-finished/速冻汤圆/速冻汤圆.md)\n" +
                "- [炸薯条](./dishes/semi-finished/炸薯条/炸薯条.md)\n" +
                "\n" +
                "### 汤与粥\n" +
                "\n" +
                "- [昂刺鱼豆腐汤](./dishes/soup/昂刺鱼豆腐汤/昂刺鱼豆腐汤.md)\n" +
                "- [番茄牛肉蛋花汤](./dishes/soup/番茄牛肉蛋花汤.md)\n" +
                "- [勾芡香菇汤](./dishes/soup/勾芡香菇汤/勾芡香菇汤.md)\n" +
                "- [金针菇汤](./dishes/soup/金针菇汤.md)\n" +
                "- [菌菇炖乳鸽](./dishes/soup/菌菇炖乳鸽/菌菇炖乳鸽.md)\n" +
                "- [罗宋汤](./dishes/soup/罗宋汤.md)\n" +
                "- [米粥](./dishes/soup/米粥.md)\n" +
                "- [排骨苦瓜汤](./dishes/soup/排骨苦瓜汤/排骨苦瓜汤.md)\n" +
                "- [皮蛋瘦肉粥](./dishes/soup/皮蛋瘦肉粥.md)\n" +
                "- [生汆丸子汤](./dishes/soup/生汆丸子汤.md)\n" +
                "- [西红柿鸡蛋汤](./dishes/soup/西红柿鸡蛋汤.md)\n" +
                "- [小米粥](./dishes/soup/小米粥.md)\n" +
                "- [银耳莲子粥](./dishes/soup/银耳莲子粥/银耳莲子粥.md)\n" +
                "- [玉米排骨汤](./dishes/soup/玉米排骨汤/玉米排骨汤.md)\n" +
                "- [紫菜蛋花汤](./dishes/soup/紫菜蛋花汤.md)\n" +
                "\n" +
                "### 饮料\n" +
                "\n" +
                "- [耙耙柑茶](./dishes/drink/耙耙柑茶/耙耙柑茶.md)\n" +
                "- [百香果橙子特调](./dishes/drink/百香果橙子特调/百香果橙子特调.md)\n" +
                "- [冰粉](./dishes/drink/冰粉/冰粉.md)\n" +
                "- [金菲士](./dishes/drink/金菲士/金菲士.md)\n" +
                "- [金汤力](./dishes/drink/金汤力/金汤力.md)\n" +
                "- [可乐桶](./dishes/drink/可乐桶.md)\n" +
                "- [奶茶](./dishes/drink/奶茶.md)\n" +
                "- [奇异果菠菜特调](./dishes/drink/奇异果菠菜特调/奇异果菠菜特调.md)\n" +
                "- [酸梅汤](./dishes/drink/酸梅汤/酸梅汤.md)\n" +
                "- [酸梅汤（半成品加工）](./dishes/drink/酸梅汤（半成品加工）.md)\n" +
                "- [泰国手标红茶](./dishes/drink/泰国手标红茶/泰国手标红茶.md)\n" +
                "- [杨枝甘露](./dishes/drink/杨枝甘露.md)\n" +
                "- [长岛冰茶](./dishes/drink/长岛冰茶.md)\n" +
                "- [B52轰炸机](./dishes/drink/B52轰炸机.md)\n" +
                "- [Mojito莫吉托](./dishes/drink/Mojito莫吉托.md)\n" +
                "\n" +
                "### 酱料和其它材料\n" +
                "\n" +
                "- [草莓酱](./dishes/condiment/草莓酱/草莓酱.md)\n" +
                "- [蒜香酱油](./dishes/condiment/蒜香酱油.md)\n" +
                "- [糖醋汁](./dishes/condiment/糖醋汁.md)\n" +
                "- [糖色](./dishes/condiment/糖色.md)\n" +
                "- [油泼辣子](./dishes/condiment/油泼辣子/油泼辣子.md)\n" +
                "- [油酥](./dishes/condiment/油酥.md)\n" +
                "- [炸串酱料](./dishes/condiment/炸串酱料.md)\n" +
                "- [蔗糖糖浆](./dishes/condiment/蔗糖糖浆/蔗糖糖浆.md)\n" +
                "\n" +
                "### 甜品\n" +
                "\n" +
                "- [奥利奥冰淇淋](./dishes/dessert/奥利奥冰淇淋/奥利奥冰淇淋.md)\n" +
                "- [草莓冰淇淋](./dishes/dessert/草莓冰淇淋/草莓冰淇淋.md)\n" +
                "- [反沙芋头](./dishes/dessert/反沙芋头/反沙芋头.md)\n" +
                "- [咖啡椰奶冻](./dishes/dessert/咖啡椰奶冻/咖啡椰奶冻.md)\n" +
                "- [烤蛋挞](./dishes/dessert/烤蛋挞/烤蛋挞.md)\n" +
                "- [魔芋蛋糕](./dishes/dessert/魔芋蛋糕/魔芋蛋糕.md)\n" +
                "- [戚风蛋糕](./dishes/dessert/戚风蛋糕/戚风蛋糕.md)\n" +
                "- [提拉米苏](./dishes/dessert/提拉米苏/提拉米苏.md)\n" +
                "- [雪花酥](./dishes/dessert/雪花酥/雪花酥.md)\n" +
                "- [芋泥雪媚娘](./dishes/dessert/芋泥雪媚娘/芋泥雪媚娘.md)\n" +
                "\n" +
                "## 进阶知识学习\n" +
                "\n" +
                "如果你已经做了许多上面的菜，对于厨艺已经入门，并且想学习更加高深的烹饪技巧，请继续阅读下面的内容：\n" +
                "\n" +
                "- [辅料技巧](./tips/advanced/辅料技巧.md)\n" +
                "- [高级专业术语](./tips/advanced/高级专业术语.md)\n" +
                "- [油温判断技巧](./tips/advanced/油温判断技巧.md)";
    }

}
