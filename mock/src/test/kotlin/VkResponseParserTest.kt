import org.junit.Assert
import org.junit.Test

class VkResponseParserTest {
    private val testResponse =
        "{\"response\":{\"items\":[{\"id\":704,\"date\":1571512033,\"owner_id\":406638754,\"from_id\":406638754,\"post_type\":\"post\",\"text\":\"Любимый котик на диете #любимка #котик # мейн-кун\",\"attachments\":[{\"type\":\"photo\",\"photo\":{\"id\":457240476,\"album_id\":-7,\"owner_id\":406638754,\"sizes\":[{\"type\":\"m\",\"url\":\"https:\\/\\/sun9-13.userapi.com\\/c856128\\/v856128540\\/131031\\/Nq8MDSLUj8o.jpg\",\"width\":130,\"height\":97},{\"type\":\"o\",\"url\":\"https:\\/\\/sun9-38.userapi.com\\/c856128\\/v856128540\\/131036\\/mIQP3bRDKFk.jpg\",\"width\":130,\"height\":98},{\"type\":\"p\",\"url\":\"https:\\/\\/sun9-9.userapi.com\\/c856128\\/v856128540\\/131037\\/raUqLXT4Jo0.jpg\",\"width\":200,\"height\":150},{\"type\":\"q\",\"url\":\"https:\\/\\/sun9-58.userapi.com\\/c856128\\/v856128540\\/131038\\/cz8uZZY6LKw.jpg\",\"width\":320,\"height\":240},{\"type\":\"r\",\"url\":\"https:\\/\\/sun9-66.userapi.com\\/c856128\\/v856128540\\/131039\\/EfZ-bvjtoTc.jpg\",\"width\":510,\"height\":383},{\"type\":\"s\",\"url\":\"https:\\/\\/sun9-45.userapi.com\\/c856128\\/v856128540\\/131030\\/FRlqYt0oQDg.jpg\",\"width\":75,\"height\":56},{\"type\":\"w\",\"url\":\"https:\\/\\/sun9-72.userapi.com\\/c856128\\/v856128540\\/131035\\/h7fs43IX4RU.jpg\",\"width\":1600,\"height\":1200},{\"type\":\"x\",\"url\":\"https:\\/\\/sun9-44.userapi.com\\/c856128\\/v856128540\\/131032\\/MOtWTnGR7pI.jpg\",\"width\":604,\"height\":453},{\"type\":\"y\",\"url\":\"https:\\/\\/sun9-14.userapi.com\\/c856128\\/v856128540\\/131033\\/QCtaLOYOb48.jpg\",\"width\":807,\"height\":605},{\"type\":\"z\",\"url\":\"https:\\/\\/sun9-53.userapi.com\\/c856128\\/v856128540\\/131034\\/laYdMMX1Exg.jpg\",\"width\":1280,\"height\":960}],\"text\":\"\",\"date\":1571511979,\"post_id\":704,\"access_key\":\"c03f36442168d8818d\"}}],\"post_source\":{\"type\":\"api\",\"platform\":\"android\"},\"comments\":{\"count\":0,\"can_post\":1,\"groups_can_post\":true},\"likes\":{\"count\":0,\"user_likes\":0,\"can_like\":1,\"can_publish\":1},\"reposts\":{\"count\":0,\"user_reposted\":0},\"views\":{\"count\":9}},{\"id\":76462,\"date\":1571510096,\"owner_id\":-169456875,\"from_id\":498501532,\"post_id\":76434,\"post_type\":\"reply\",\"text\":\"100. На данный момент самые активные тэги: \\n" +
                "#Единорожек \\n" +
                "101. Еще #Diligitis \\n" +
                "102. И #Котик \\n" +
                "103. ПСнО слово \\\"фрукт\\\" употребляется ровно 8 раз \\n" +
                "104. А овощ 9. \\n" +
                "105. #Единорожек чаще использует этот смайл \uD83D\uDDA4 \\n" +
                "106. А #Diligitis этот \uD83D\uDC9B \\n" +
                "107. #Diligitis пишет советы. \\n" +
                "108. А еще рубрику удивительное\\n" +
                "112. В рубреке мечты используют цитаты \\n" +
                "113. Анастейша лайкает почти все комментарии \\n" +
                "114. И отвечает тоже \\n" +
                "114. Ник Арии имеет биологические корни.\\n" +
                "115. Первый работник из жителей была Амина Осокина.\\n" +
                "116. Раньше за ПСнО отвечала Элла Дорис.\\n" +
                "117. До нее какие-то два мужика. \\n" +
                "118. Мартин брал ПСнО в свои владения на неделю.\\n" +
                "119. На ПснО есть рубрика реклама \\n" +
                "120. На ней рекламируют другие группы ролевой \\n" +
                "121. И инсаграм тоже.\\n" +
                "122. Когда-то там проводился конкурс.\\n" +
                "123. Его проводил Мартин за активности в ПСнО на стикеры.\\n" +
                "124. На самой первой аватаре островов — костер, что является символом ПСнО.\\n" +
                "125. С #Аорта есть 6 постов \\n" +
                "126. У #Аорты 5 постов \\n" +
                "127. Плюс один про факты о чудесной Арии.\\n" +
                "128. Когда-то в ПСнО были красивые подборки и кулинарные рецепты.\\n" +
                "129. Мэй раньше писала посты с 13:00 до 18:00.\\n" +
                "130. Стефани Ферданс была вторым работником ПСнО с Аминой Осокиной.\\n" +
                "131. Есть замечательная рубрика признания \\n" +
                "132. Где всегда очень милые посты.\\n" +
                "133. Проводились прекрасные интерактивы на км.\\n" +
                "134. Многие активисты хоть раз отправляли туда факты\\n" +
                "135. Иногда не по своей воле\\n" +
                "136. ПСнО было местом самой сильной битвы Тартла и Муджереса.\\n" +
                "137. Первый пост в ПСнО слова Мартина для Сонечки.\\n" +
                "138. Там был прощальный пост лучшего Джеффа (Екатерины Седовой).\\n" +
                "139. ПСнО было любимой локацией Мартина.\\n" +
                "140. Есть хэштег Счастье.\\n" +
                "141. И ник #Счастье.\\n" +
                "142. Обычно опросы идут под хэштегом #Успех\\n" +
                "143. #Фауст из прекрасного произведения искусства.\\n" +
                "144. Были интерактивы при недели Тартла и Муджереса.\\n" +
                "145. ПСнО сближало и мирило людей из-за постов.\\n" +
                "146. Группа упоминается в ссылках Законов джунглей\\n" +
                "147. О том что Мартин — Бог говорится трижды\\n" +
                "148. Мэй упоминается в записях 35 раз\\n" +
                "149. А тот факт, что Мэй — богиня, всего 2 раза\\n" +
                "150. Именно в ПСнО было продолжение Праздника Смерти.\",\"marked_as_ads\":0,\"post_source\":{\"type\":\"vk\"},\"comments\":{\"count\":0,\"can_post\":0},\"likes\":{\"count\":1,\"user_likes\":0,\"can_like\":1,\"can_publish\":1},\"reposts\":{\"count\":0,\"user_reposted\":0}}],\"count\":1000,\"total_count\":206109,\"next_from\":\"2\\/-169456875_76462\"}}"

    @Test
    fun parseResponseTest() {
        val info = testResponse.parseResponse()
        Assert.assertEquals(2, info.size)
        Assert.assertEquals(info[0], VkInfo(704, 406638754, 1571512033))
        Assert.assertEquals(info[1], VkInfo(76462, -169456875, 1571510096))
    }
}