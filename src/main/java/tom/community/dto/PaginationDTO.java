package tom.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页的数据传输对象
 */
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;//是否没有向前按钮
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//当前页面
    private List<Integer> pages=new ArrayList<>();//展示的页面
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page + i);
            }
        }

        //是否展示上一页按钮
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页按钮
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }

    }
}
