package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xc27.common.R;
import top.xc27.entity.AddressBook;
import top.xc27.entity.User;
import top.xc27.service.AddressBookService;
import top.xc27.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;
import top.xc27.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 17108
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-10-29 18:11:58
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService{
    @Autowired
    private UserService userService;

    @Override
    public R<List<AddressBook>> getAddressBooks(User user) {
        String email = user.getEmail();
        System.out.println("email = " + email);
        Long id = userService.getUserByEmail(email).getId();
        List<AddressBook> list = getAddressBooksByUserId(id);
        return R.success(list);
    }

    public List<AddressBook> getAddressBooksByUserId(Long id) {
        return this.list(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, id)
                .eq(AddressBook::getIsDeleted,0));
    }

    @Override
    public R<AddressBook> getDefaultAddressBook(Long uid) {
        return R.success(getDefaultByUid(uid));
    }

    @Override
    public R<String> saveAddressBook(AddressBook addressBook, HttpServletRequest request) {
        addressBook.setId(IdUtil.getSnowflakeNextId());
        Long id = (Long) request.getSession().getAttribute("user");
        addressBook.setUserId(id);
        this.save(addressBook);
        return R.success("新增成功!");
    }

    @Override
    public R<String> editAddressBookDefault(Long id,HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute("user");
        AddressBook defaultAddressBook = getDefaultByUid(uid);
        if(ObjectUtil.isNotEmpty(defaultAddressBook)){
            defaultAddressBook.setIsDefault(0);
            this.updateById(defaultAddressBook);
        }
        AddressBook addressBook = getAddressBookById(id);
        addressBook.setIsDefault(1);
        this.updateById(addressBook);
        return R.success("设置默认地址成功!");
    }

    private AddressBook getDefaultByUid(Long uid) {
        LambdaQueryWrapper<AddressBook> queryWrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapper<AddressBook> wrapper = queryWrapper
                .eq(AddressBook::getUserId, uid)
                .eq(AddressBook::getIsDeleted, false)
                .eq(AddressBook::getIsDefault,true);
        return this.getOne(wrapper);
    }

    @Override
    public AddressBook getAddressBookById(Long id) {
        LambdaQueryWrapper<AddressBook> queryWrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapper<AddressBook> wrapper = queryWrapper.eq(AddressBook::getId, id).eq(AddressBook::getIsDeleted, false);
        return this.getOne(wrapper);
    }

    @Override
    public R<AddressBook> getAddressBook(Long id) {
        AddressBook addressBook = getAddressBookById(id);
        if(ObjectUtil.isEmpty(addressBook)){
            return R.error("没有查询到对应地址!");
        }
        return R.success(addressBook);
    }

    @Override
    public R<String> deleteAddressBook(Long ids) {
        AddressBook addressBook = getAddressBookById(ids);
        addressBook.setIsDeleted(1);
        this.updateById(addressBook);
        List<AddressBook> bookList = getAddressBooksByUserId(addressBook.getUserId());
        if(bookList.size() == 1){
            AddressBook book = bookList.get(0);
            book.setIsDefault(1);
            this.updateById(book);
        }
        return R.success("删除成功!");
    }

    @Override
    public R<String> editAddressBook(AddressBook addressBook, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute("user");
        addressBook.setUpdateUser(uid);
        this.updateById(addressBook);
        return R.success("修改成功!");
    }
}




