package com.ace.cms.service;

import com.ace.cms.BaseServiceTest;
import com.ace.cms.cache.UserCache;
import com.ace.cms.dto.ContentDto;
import com.ace.cms.dto.ReptilianNewsDto;
import com.ace.cms.exceptions.SequenceException;
import com.ace.cms.mapper.ContentMapper;
import com.ace.cms.mapper.ReptilianNewsMapper;
import com.ace.cms.vo.comment.Comment;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.news.NewsVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class ContentServiceTest extends BaseServiceTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Test
    public void test() {

        List<Content> result = contentService.getListByChannelId(75, 1 ,3);

        System.out.println(result);
    }

    @Test
    public void test2() {
        List<Comment> result = commentService.getCommentById(200l,false,1,10);
        System.out.println(result);
    }

    @Test
    public void test3() {
        commentService.add(2l,0l,0L,"测试", 0);
    }


    @Autowired
    private UserCache userCache;

    @Test
    public void test4() {
        System.out.println(userCache.get(1L));
    }

    @Test
    public void test5() {
        contentService.add(1l,"test","www.test1","www.test2", 0);
    }

    @Test
    public void test6() {
        contentService.getListByName("搜索",1,10);
    }

    @Test
    public void test7() {
        NewsVo newsVo = contentService.getNews(327l);
        System.out.println(newsVo);
        System.out.println(newsVo.getTxt().size());
        System.out.println(newsVo.getTxt().get(0).get("text"));
        System.out.println(newsVo.getTxt());
    }

    @Autowired
    private ContentMapper contentMapper;

    @Test
    public void test10() throws SequenceException {
        ReptilianNewsDto dto = new ReptilianNewsDto();
        dto.setContext("[{\"text\":\"7月11日，商务部新闻发言人就美方公布拟对我2000亿美元输美产品加征关税清单发表谈话表示：美方以加速升级的方式公布征税清单，是完全不可接受的，我们对此表示严正抗议。美方的行为正在伤害中国，伤害全世界，也正在伤害其自身，这种失去理性的行为是不得人心的。中方对美方的行为感到震惊，为了维护国家核心利益和人民根本利益，中国政府将一如既往，不得不作出必要反制。\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/9D8C89AED9883A0756B5A8D1D09104D104770F4E_size40_w728_h381.jpeg\"},{\"text\":\"与此同时，我们呼吁国际社会共同努力，共同维护自由贸易规则和多边贸易体制，共同反对贸易霸凌主义。我们将立即就美方的单边主义行为向世界贸易组织追加起诉。\"},{\"text\":\"当晚，《央视财经评论》邀请到商务部研究院国际市场研究所副所长白明以及央视财经评论员刘戈做客演播室，深入解析。\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/C988AE2307A5CD6B0AF3637344CE7802670DE89F_size53_w727_h395.jpeg\"},{\"text\":\"为打贸易战不择手段美贸易霸凌主义尽显无遗\"},{\"text\":\"白明：美国为打贸易战而不择手段\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/5566897E1C643AE144FD859D73C4602D8A040200_size47_w729_h388.jpeg\"},{\"text\":\"美方这一次打贸易战，可以说是不择手段的。美国政府的指导思想是以美国利益最大化为目的。现在美国政府不能够容忍中国的发展越来越接近美国。在这种情况下，你出台什么样的政策，无论是谈判或者是反制，最终美方都是想一味地不断加码。\"},{\"text\":\"商务部研究院国际市场研究所副所长白明：\"},{\"text\":\"刘戈：美“漫天要价”行为不得人心\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/D424EAE359250F961F0B43FDA5F6233845CC3F9D_size34_w720_h391.jpeg\"},{\"text\":\"美国漫天要价的行为的确不得人心。有人统计过，大概有百分之五十几要涉及到美国企业、日本企业、韩国企业等很多企业，不仅是中国企业，如果把单子真的开到2000亿，实际上政策会陷入一个悖论，那就是开出这些单子是为了抑制中国的高科技产品，但实际上，完全由中资企业出口到美国的高科技产品是没有那么多的，如果开不出来这个单子，它就要转向传统服装鞋帽这些产品，和初衷就不相符了。\"},{\"text\":\"央视财经评论员刘戈：\"},{\"text\":\"美国行为害己害人破坏国际经济秩序\"},{\"text\":\"白明：美国单边主义对全球经贸带来损害\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/40C195F218133F9A1D3304F4A17E6BC4587B6B9B_size51_w733_h411.jpeg\"},{\"text\":\"美国政府的单边主义，用另一句话表示，就是无视世贸组织的规则。这些年世界经济贸易的发展都是按照这些规则进行的。现在它破坏了这个规则，无论是哪一个方面，它都对世界经济秩序充满了不确定性，包括资本市场、大宗商品市场，也会对不确定性有所反应，投资者会规避风险。按理说世贸组织，如果有争端，包括301调查，你可以用争端解决机制去解决问题，但是现在美方是自己发起了加征关税，用国内法替代WTO规则，相当于把WTO规则给边缘化了，如果你需要磋商，应该用WTO的争端解决机制，不要自行出台，用单边主义去制裁别人，随意地采取任意的行为。\"},{\"text\":\"商务部研究院国际市场研究所副所长白明：\"},{\"text\":\"刘戈：美与多国作对将给全球经贸蒙上阴影\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/3FB293243A9753C784AA093B0CBBA09F3F984B0E_size36_w708_h389.jpeg\"},{\"text\":\"美国的这种措施与多国作对，实际上对世界经贸发展蒙上了阴影。在中国生产的产品有很多是外资生产的，现在如果对中国进口美国零配件进行关税的限制，这时候美国就要重新打造它的产业链。\"},{\"text\":\"央视财经评论员刘戈：\"},{\"text\":\"坚定信心保持定力中国经济能应对新挑战\"},{\"text\":\"白明：用转型升级应对贸易战升级\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/F88C458B5684E185C469DA574F6A5F178AD15CE1_size40_w730_h380.jpeg\"},{\"text\":\"这场贸易战确实给中国带来了一些挑战，但是我们还是一步一个脚印推进，包括从要素驱动、资本驱动走向创新驱动，打造核心竞争力，因为产业的水平提升了，外贸的水平自然就提升了。我们是开放的，但是，如果要采取一系列措施阻挡中国的发展，美国可能会失去一些机会。我国现在采取了一系列开放措施，比如，扩大消费品进口、扩大市场准入、改善营商环境、重视知识产权保护这一系列措施，都是推进转型升级的，用这种转型升级应对贸易战的升级是最有效的。\"},{\"text\":\"商务部研究院国际市场研究所副所长白明：\"},{\"text\":\"刘戈：贸易战没有剧本做好长期应战准备\"},{\"img\":\"http://p0.ifengimg.com/pmop/2018/0712/78B851400EFBC2B5187A38DCB01B907CB480EDD8_size41_w722_h395.jpeg\"},{\"text\":\"中国具备完备的工业体系。联合国有一个对于全世界工业体系的分类，它分成39个工业大类，529个工业的小类，从大类到小类，中国的产品一样不缺。\"},{\"text\":\"央视财经评论员刘戈：\"},{\"text\":\"我国不会闭关锁国。目前无论是整机还是零配件都有大量的外国公司在中国生产，所以形成一个完备体系之后，对货物贸易发生变化的耐受能力就很强，美国现在也有强大的制造业，在高科技领域占有优势，整体来说它的品类不全，它和世界各国贸易依赖程度更高。\"},{\"text\":\"现在看贸易战完全没有剧本，因为美国政府现在的这种行为也的确没有按照贸易纠纷规则来解决，所以要做好长期应对的准备，可能在未来很长时间里面，它会成为一种常态。\"}]");
        dto.setOtherId(1l);
        dto.setPicUrl("");
        dto.setPublishTime(new Date());
        dto.setTitle("test");
        contentService.batchNews(dto);
    }

    @Autowired
    private ReptilianNewsMapper reptilianNewsMapper;

    @Test
    public void test8() throws SequenceException {
        List<ReptilianNewsDto> list = reptilianNewsMapper.get();

        for(ReptilianNewsDto dto : list) {
            try{
                contentService.batchNews(dto);
            }catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

    }

}
