package top.xc27.service;

import top.xc27.common.R;
import top.xc27.entity.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xc27.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 17108
* @description 针对表【address_book(地址管理)】的数据库操作Service
* @createDate 2022-10-29 18:11:58
*/
public interface AddressBookService extends IService<AddressBook> {

    R<List<AddressBook>> getAddressBooks(User user);

    R<String> saveAddressBook(AddressBook addressBook, HttpServletRequest request);

    R<String> editAddressBookDefault(Long id,HttpServletRequest request);

    AddressBook getAddressBookById(Long id);

    R<AddressBook> getAddressBook(Long id);

    R<String> deleteAddressBook(Long id);

    R<String> editAddressBook(AddressBook addressBook, HttpServletRequest request);

    List<AddressBook> getAddressBooksByUserId(Long id);

    R<AddressBook> getDefaultAddressBook(Long uid);
}
