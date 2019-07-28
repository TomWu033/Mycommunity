package tom.community.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    @Override
    //初始化
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWord.txt");
            InputStreamReader read=new InputStreamReader(is);
            BufferedReader bufferedReader=new BufferedReader(read);
            String lineTxt;
            while((lineTxt=bufferedReader.readLine())!=null){
                addWord(lineTxt.trim());
            }
            read.close();
        }catch (Exception e){

        }
    }

    //添加敏感词
    private void addWord(String lineText){
        TrieNode tempNode=rootNode;
        for (int i=0;i<lineText.length();i++){
            Character c=lineText.charAt(i);
            if (isSymbol(c)){
                continue;
            }
            TrieNode node=tempNode.getSubNode(c);
            if (node==null){
                node=new TrieNode();
                tempNode.addSubNode(c,node);//添加子节点，当第一次时
            }
            tempNode=node;
            if (i==lineText.length()-1){
                tempNode.setKeywordEnd(true);//设置为最后的一个节点
            }
        }
    }

    //构建前缀树
    private class TrieNode{
        private boolean end = false;//是不是某一个敏感词的结尾

        //当前节点下所有的子节点
        private Map<Character,TrieNode> subNodes=new HashMap<>();

        //
        public void addSubNode(Character key, TrieNode node){
            subNodes.put(key,node);
        }

        //这个前缀字符是否有子节点
        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeywordEnd(){return end;}
        void setKeywordEnd(boolean end){
            this.end=end;
        }
    }

    private TrieNode rootNode=new TrieNode();//根节点

    //
    private boolean isSymbol(char c){
        int ic=(int)c;
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0x2E80||ic>0x9FFF);//0x2E80到0x9FFF代表东亚文字，前面为不是英文
    }

    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return text;
        }
        StringBuilder result=new StringBuilder();
        String replacement="***";
        TrieNode tempNode=rootNode;
        int begin=0;
        int position=0;
        while(position<text.length()){
            char c=text.charAt(position);
            if (isSymbol(c)){
                if (tempNode==rootNode){
                    result.append(c);
                    ++begin;
                }
                position++;
                continue;
            }
            tempNode=tempNode.getSubNode(c);
            //没有
            if (tempNode==null){
                result.append(text.charAt(begin));
                position=begin+1;
                begin=position;
                tempNode=rootNode;
            }
            else if (tempNode.isKeywordEnd()){
                //走到叶节点，即发现敏感词
                result.append(replacement);//打码
                position=position+1;
                begin=position;
                tempNode=rootNode;
            }
            else{
                ++position;
            }
        }
        result.append(text.substring(begin));//最后一串
        return result.toString();
    }
}
