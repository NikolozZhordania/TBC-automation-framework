package ge.tbc.testautomation.tbcbankapp.ui.data.providers;

import ge.tbc.testautomation.tbcbankapp.ui.data.db.session.MyBatisSessionProvider;
import ge.tbc.testautomation.tbcbankapp.ui.data.db.mapper.BranchMapper;
import ge.tbc.testautomation.tbcbankapp.ui.data.db.model.Branch;
import org.testng.annotations.DataProvider;

import java.util.List;

public class BranchDataProvider {

    @DataProvider(name = "atmTestCases")
    public static Object[][] atmTestCases() {
        List<Branch> cases = MyBatisSessionProvider.execute(
                BranchMapper.class,
                BranchMapper::findAllEnabled
        );
        return cases.stream()
                .map(tc -> new Object[]{tc})
                .toArray(Object[][]::new);
    }
}
