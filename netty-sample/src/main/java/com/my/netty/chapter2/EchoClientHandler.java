package com.my.netty.chapter2;

import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {	
		String id = ctx.channel().attr(EchoClient.id).get();
		System.out.println("send id --- " + id);
		StringBuilder builder = createContent();
		System.out.println("total size of the  packet --- " + StringUtils.getBytesUtf8(builder.toString()).length);
		byte[] content = builder.toString().getBytes("UTF-8");
		ByteBuf bf = Unpooled.buffer(content.length);
		bf.writeBytes(content);
		
		System.out.println("size in buffer --- " + bf.array().length);
		
		ctx.writeAndFlush(bf);
		//bf.release();
	}

	private StringBuilder createContent() {
		StringBuilder builder = new StringBuilder();
		builder.append("#################################################################################################");
		builder.append("简介");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("发动机及涡轮机联盟弗里的希哈芬股份有限公司（简称MTU）");
		builder.append("MTU为戴姆勒-奔驰集团属下公司，是世界领先的柴油发动机制造商，其柴油发动机功率从35kw-9000kw，广泛用于舰船、重型汽车和工程机械、铁路机车。MTU不仅仅制造柴油发动机，还制造面向最终用户的完整成套产品。");
		builder.append("历史");
		builder.append("自1899年起，MTU的前身Friedrichshafen engine manufacturer，开始重型发动机制造，以其技术的开创性，为世界发动机行业树立了诸多里程碑。 1919年的凡尔赛条约，禁止德国公司的产品用于飞行器。因此Friedrichshafen engine manufacturer发动机更多地用于铁路机车和坦克、军舰动力，曾在1936年创造了铁路史上160公里时速的记录。在1934年，Friedrichshafen engine manufacturer制造了世界上首台涡轮增压的柴油发动机。此后不断的创新，使Friedrichshafen engine manufacturer成为世界上最大、最先进的发动机制造商之一。");
		builder.append("1960年梅赛德斯-奔驰（Mercedes-Benz）收购了Friedrichshafen engine manufacturer。 MTU公司前身，德国Friedrichshafen(弗里的希哈芬)制造过Zeppelin（齐柏林硬式飞艇）发动机，Maybach、Zeppelin（迈巴赫、齐柏林）超级豪华轿车及Maybach引擎等最昂贵的德国工业产品，Friedrichshafen的创办者、天才设计师威廉·迈巴赫在1907年创办Friedrichshafen以前作为Daimler-Motoren-Gesellschaft(DMG)的技术总监，是第一辆梅赛德斯-奔驰汽车（1901年）的设计者，这是汽车历史上公认的第一辆现代轿车，而凭借于此，威廉·迈巴赫在汽车界也被尊称为“设计之父”。");
		builder.append("Friedrichshafen在1960年并入Daimler集团，一个新品牌诞生了：Motoren-und Turbinen-Union Friedrichshafen GmbH(发动机及涡轮机联盟弗里的希哈芬股份有限公司，简称MTU)。自此，MTU Friedrichshafen工厂，成为了铁路机车引擎，舰船引擎、柴油发电机组引擎的主要制造商。");
		builder.append("产品及声誉");
		builder.append("MTU作为陆用、水用和铁路推动系统以及发电设备引擎的供应商，MTU以其领先的技术、高可靠性的产品以及一流的售后服务，在世界范围内享有盛誉。MTU一向以输出功率大、效率高、体积小并且经久耐用而闻名于世，但以往，偏高的价格也限制了MTU的产品绝大部分只应用在军舰、坦克、核电、运输船、铁路机车等要害领域。");
		builder.append("90年代以后");
		builder.append("20世纪90年代MTU凭借其四冲程柴油机全球领先的技术与著名的美国军用柴油机最大的供应商—底特律柴油机公司（detroit diesel company，简称DDC）进行合作，现在MTU已经全资拥有DDC，而detroit diesel 正是以其独特的两冲程机享誉市场达一百多年并长期稳定占有美国军用柴油机75%的份额，两强的技术融合最终导致了新型柴油机的诞生——MTU/DDC2000系列及4000系列柴油发动机。2000年，MTU与DDC正式合并，至此，MTU公司成为世界上发动机技术最先进，功率范围最大的发动机供应商。");
		builder.append("目前世界上绝大部分主战坦克使用的都是MTU系列柴油机，诸如“挑战者”2E，豹2系列。");
		builder.append("#################################################################################################");
		return builder;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		String id = ctx.channel().attr(EchoClient.id).get();
		System.out.println("read id --- " + id + " Client received: " + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
