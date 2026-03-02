package ge.tbc.testautomation.tbcbankapp.ui.data.db.mapper;

import ge.tbc.testautomation.tbcbankapp.ui.data.db.model.Branch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BranchMapper {

    @Select("SELECT * FROM atm_test_cases WHERE enabled = 1")
    List<Branch> findAllEnabled();

    @Select("SELECT * FROM atm_test_cases WHERE city = #{city} AND enabled = 1")
    List<Branch> findByCity(String city);

    @Select("SELECT * FROM atm_test_cases WHERE id = #{id}")
    Branch findById(int id);
}
