package github.zero.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(int expireSeconds, String prefix) {
		super( prefix);
	}


	public static MiaoshaKey isGoodsOver = new MiaoshaKey( 0,"go");
	public static KeyPrefix getMiaoshaPath = new MiaoshaKey(60,"mp");
	public static KeyPrefix getMiaoshaVerifyCode = new MiaoshaKey(300,"vc");

}
