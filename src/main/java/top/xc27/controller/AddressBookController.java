package top.xc27.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.AddressBook;
import top.xc27.entity.User;
import top.xc27.service.AddressBookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("addressBook")
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping("list")
    public R<List<AddressBook>> getAddressBooks(@RequestBody User user){
        return addressBookService.getAddressBooks(user);
    }

    @PostMapping
    public R<String> saveAddressBook(@RequestBody AddressBook addressBook, HttpServletRequest request){
        return addressBookService.saveAddressBook(addressBook,request);
    }

    @PutMapping("default")
    public R<String> editAddressBookDefault(@RequestBody AddressBook addressBook,HttpServletRequest request){
        if(null == addressBook.getId()){
            return R.error("id参数缺失!");
        }
        return addressBookService.editAddressBookDefault(addressBook.getId(),request);
    }

    @GetMapping("default")
    public R<AddressBook> getDefaultAddressBook(HttpServletRequest request){
        Long uid = (Long) request.getSession().getAttribute("user");
        if(null == uid){
            return R.error("id参数缺失!");
        }
        return addressBookService.getDefaultAddressBook(uid);
    }

    @GetMapping("/{id}")
    public R<AddressBook> getAddressBook(@PathVariable Long id){
        if(null == id){
            return R.error("id参数缺失!");
        }
        return addressBookService.getAddressBook(id);
    }

    @DeleteMapping
    public R<String> deleteAddressBook(Long ids){
        if(null == ids){
            return R.error("id参数缺失!");
        }
        return addressBookService.deleteAddressBook(ids);
    }

    @PutMapping
    public R<String> editAddressBook(@RequestBody AddressBook addressBook,HttpServletRequest request){
        return addressBookService.editAddressBook(addressBook,request);
    }
}
