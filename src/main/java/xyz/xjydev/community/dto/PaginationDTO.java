package xyz.xjydev.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 28767
 * @date: 2020/9/8 10:29
 */

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages=new ArrayList<>();

    public void setPagination(Integer totalPage, Integer page, Integer size) {

        this.page=page;
        this.totalPage=totalPage;
        // 显示分页列表
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        // 是否展示上一页
        if (page == 1) {
            this.showPrevious = false;
        } else {
            this.showPrevious = true;
        }

        // 是否展示下一页
        if(page.equals(totalPage)){
            this.showNext=false;
        }else{
            this.showNext=true;
        }

        // 是否展示第一页
        if(pages.contains(1)){
            this.showFirstPage=false;
        }else{
            this.showFirstPage=true;
        }

        // 是否展示最后一页
        if(pages.contains(totalPage)){
            this.showLastPage=false;
        }else{
            this.showLastPage=true;
        }
    }
}
