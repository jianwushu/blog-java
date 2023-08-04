package com.ruoyi.web.controller.blog;


import java.util.Arrays;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.entity.Comment;
import com.ruoyi.system.entity.dto.CommentDTO;
import com.ruoyi.system.entity.vo.CommentVo;
import com.ruoyi.system.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;


import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论Controller
 *
 * @author ruoyi
 * @date 2020-11-15
 */

@RestController
@RequestMapping("/blog/comment" )
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/tree")
    public TableDataInfo getList(CommentVo commentVo){

        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();
        if(commentVo.getBlogId()!=null){
            qw.eq(Comment::getBlogId,commentVo.getBlogId());
        }
        qw.isNull(Comment::getParentId);
        List<CommentDTO> list = commentService.getList(qw);
        return getDataTable(list);
    }

    @GetMapping("/count")
    public AjaxResult getCount(){

        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();

        qw.eq(Comment::getDeleted,0);

        String count = commentService.getCount(qw);
        return AjaxResult.success(count);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody CommentVo commentVo){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo,comment);
        commentService.save(comment);
        return AjaxResult.success("添加成功");
    }
    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Comment comment) {
        startPage();
        /**
         *  Wrappers.lambdaQuery(new Comment()) 放一个控制，避免mp全值匹配为eq 造成模糊查询失效
         */
        LambdaQueryWrapper<Comment> lqw = Wrappers.lambdaQuery(new Comment());
        if (StringUtils.isNotBlank(comment.getAvatar())){
            lqw.eq(Comment::getAvatar ,comment.getAvatar());
        }
        if (StringUtils.isNotBlank(comment.getNickName())){
            lqw.like(Comment::getNickName ,comment.getNickName());
        }
        if (StringUtils.isNotBlank(comment.getEmail())){
            lqw.like(Comment::getEmail ,comment.getEmail());
        }
        if (StringUtils.isNotBlank(comment.getText())){
            lqw.like(Comment::getText ,comment.getText());
        }
        if (StringUtils.isNotBlank(comment.getIp())){
            lqw.like(Comment::getIp ,comment.getIp());
        }
        if (comment.getBlogId() != null){
            lqw.eq(Comment::getBlogId ,comment.getBlogId());
        }
        if (comment.getParentId() != null){
            lqw.eq(Comment::getParentId ,comment.getParentId());
        }
        if (comment.getVersion() != null){
            lqw.eq(Comment::getVersion ,comment.getVersion());
        }
        if (comment.getDeleted() != null){
            lqw.eq(Comment::getDeleted ,comment.getDeleted());
        }
        List<Comment> list = commentService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:comment:export')" )
    @Log(title = "评论" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Comment comment) {
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<Comment>(comment);
        List<Comment> list = commentService.list(lqw);
        ExcelUtil<Comment> util = new ExcelUtil<Comment>(Comment. class);
        return util.exportExcel(list, "comment" );
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:comment:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(commentService.getById(id));
    }

    /**
     * 新增评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:add')" )
    @Log(title = "评论" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Comment comment) {
        return toAjax(commentService.save(comment) ? 1 : 0);
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:edit')" )
    @Log(title = "评论" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Comment comment) {
        return toAjax(commentService.updateById(comment) ? 1 : 0);
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:remove')" )
    @Log(title = "评论" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(commentService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
