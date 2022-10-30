package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-10-29 18:11:58
* @Entity top.xc27.entity.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




