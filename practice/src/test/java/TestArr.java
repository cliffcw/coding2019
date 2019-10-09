import lombok.Data;

/**
 * @program: base
 * @description:
 * @author: cliffcw
 * @create: 2019-08-25 13:19
 */
@Data
public class TestArr implements Cloneable {
    public String id;
    public String name;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
