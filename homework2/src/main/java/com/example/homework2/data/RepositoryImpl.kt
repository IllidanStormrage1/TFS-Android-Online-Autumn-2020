package com.example.homework2.data

import com.example.homework2.data.dto.NewsResponse
import com.example.homework2.domain.MainRepository
import com.example.homework2.domain.Post
import com.google.gson.Gson

class RepositoryImpl : MainRepository {

    private val cachedPosts: MutableList<Post> by lazy {
        Gson().fromJson(mockJson,
            NewsResponse::class.java).response.asDomainModel()
    }

    override fun getAllPosts(): List<Post> = cachedPosts

    override fun likePost(id: Int) {
        val item = cachedPosts.find { it.id == id }
        item?.let {
            if (item.isFavorite)
                item.likesCount--
            else
                item.likesCount++
            item.isFavorite = item.isFavorite.not()
        }
    }
}

const val mockJson = """
{"response":{"items":[{"source_id":-54530371,"date":1601823780,"can_doubt_category":false,"can_set_category":false,"topic_id":7,"post_type":"post","text":"Functional Programming in Java (2017) \nАвтор: Pierre-Yves Saumont \nКоличество страниц: 472 \n \nКнига учит разработчиков Java, как включить самые мощные преимущества функционального программирования в новый и имеющийся код на Java. Вы научитесь функционально думать о задачах программирование на Java и использовать функциональный подход, чтобы упростить понимание, оптимизацию, поддержку и масштабирование ваших приложений. \n \nВ книге рассматриваются следующие темы: \n✔Написание кода, который легче читать; \n✔Безопасное параллельное программирование; \n✔Обработка ошибок без исключений; \n✔Фичи Java 8, такие как лямбды, ссылки на методы и функциональные интерфейсы; \n✔И многие другие. \n \nДостоинства: \n➕Подходит для изучения функционального подхода, даже если вы не знаете Java; \n➕Глубокое погружение в тему. \n \nНедостатки: \n➖Старая версия Java.\n\n#java@proglib #book@proglib","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601747726,"id":457268798,"owner_id":-54530371,"has_tags":false,"access_key":"9f1b22c7e8f690d3e0","sizes":[{"height":130,"url":"https:\/\/sun7-9.userapi.com\/hVbmk3lm8ixDNSbKc6FyhIdqjL_lafwUxM7mUg\/65pfTxOwFaI.jpg","type":"m","width":130},{"height":130,"url":"https:\/\/sun7-9.userapi.com\/hVbmk3lm8ixDNSbKc6FyhIdqjL_lafwUxM7mUg\/65pfTxOwFaI.jpg","type":"o","width":130},{"height":200,"url":"https:\/\/sun7-9.userapi.com\/UkTJRctMcVS8WYmlfOB7T8upxd7TQOgJ-gEswA\/PwkEzjRWpzk.jpg","type":"p","width":200},{"height":320,"url":"https:\/\/sun7-8.userapi.com\/lGjr8e-le2aWcrO1NOKACQy3qJwce37mbj6LVQ\/SU_bp93t4Xc.jpg","type":"q","width":320},{"height":510,"url":"https:\/\/sun7-9.userapi.com\/pSPziwWFjV45XMfv0xGztUM4fa5QKc8KVQdz1Q\/AO8XhUtBNLE.jpg","type":"r","width":510},{"height":75,"url":"https:\/\/sun7-8.userapi.com\/NvRgIBammjrVVVZoQ8i2kqNEBuhyNnseBJQGZA\/ScpJmws7G0I.jpg","type":"s","width":75},{"height":604,"url":"https:\/\/sun7-8.userapi.com\/iCZcio2AaQXUswiyyfN0kD6oN0JQdiJdJZc-3A\/Qva3aPCTd3E.jpg","type":"x","width":604},{"height":807,"url":"https:\/\/sun7-8.userapi.com\/AGiro_c9v93skLPlLdTOwPmcBTvYV_Ey3VWHdA\/XFAM50AHjlg.jpg","type":"y","width":807},{"height":1080,"url":"https:\/\/sun7-7.userapi.com\/s1YR1uXOcaPeIArZBSvW2xZ5WWuU9rlATgCivw\/63RHgaaLL3g.jpg","type":"z","width":1080}],"text":"","user_id":100}},{"type":"doc","doc":{"id":570316930,"owner_id":255577237,"title":"Pierre-Yves Saumont - Functional Programming in Java_ How functional techniques improve your Java programs (2017, Manning Pub..","size":6703876,"ext":"pdf","date":1601747684,"type":1,"url":"https:\/\/vk.com\/doc255577237_570316930?hash=10b7c7fea5805e7c12&dl=GEYDCMJTHA4TSNQ:1601825627:783fb5b670d9418688&api=1&no_preview=1&module=feed","access_key":"a854b50df198aa8dbb"}}],"post_source":{"type":"vk"},"comments":{"count":0,"can_post":1,"groups_can_post":true},"likes":{"count":13,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":3,"user_reposted":0},"views":{"count":4307},"is_favorite":true,"post_id":306763,"type":"post"},{"source_id":-54530371,"date":1601817000,"can_doubt_category":false,"can_set_category":false,"category_action":{"action":{"target":"internal","type":"open_url","url":"vk.com\/feed?section=custom&feed_id=discover_category\/7"},"name":"IT"},"topic_id":7,"post_type":"post","text":"Secure By Design (2019) \nАвторы: Daniel Deogun, Dan Bergh Johnsson, Daniel Sawano \nКоличество страниц: 410 \n \nРазработчикам нужно создавать программное обеспечение безопасным. Но нельзя тратить все свое время на безопасность. Ответ - использовать хорошие принципы проектирования, инструменты и образ мышления, которые по умолчанию предоставляют безопасность. Secure by Design учит разработчиков использовать дизайн для обеспечения безопасности при разработке программного обеспечения. Эта книга полна шаблонов и передовых практик, которые вы можете напрямую применить в разработке. \n \nДостоинства: \n➕Одна из лучших книг по теме; \n➕Для примеров кода используется Java. \n \nНедостатки: \n➖Не замечено.\n\n#java@proglib #book@proglib","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601747606,"id":457268797,"owner_id":-54530371,"has_tags":false,"access_key":"3ee4a4de4272c1c7db","sizes":[{"height":130,"url":"https:\/\/sun7-6.userapi.com\/1CAQPlqf4-0wKiRPpptMIG0XgTFMFwp6vlcrOw\/f6dZGkc42Dg.jpg","type":"m","width":130},{"height":130,"url":"https:\/\/sun7-6.userapi.com\/1CAQPlqf4-0wKiRPpptMIG0XgTFMFwp6vlcrOw\/f6dZGkc42Dg.jpg","type":"o","width":130},{"height":200,"url":"https:\/\/sun7-8.userapi.com\/3YPW9O5Y5fv56GR_1GWlxDZ3C_CIvmGSvCkDqw\/RVbg4TMQuW4.jpg","type":"p","width":200},{"height":320,"url":"https:\/\/sun7-6.userapi.com\/VSulxN2_G8KAJbQmvH08qXhDUTNDgRNXBzdJ3Q\/guxux9z5XF0.jpg","type":"q","width":320},{"height":510,"url":"https:\/\/sun7-8.userapi.com\/6su6EMALqS7ah4_V0XbZL7q9iLKrR3p3HfQVQA\/1b8GfJwXNqs.jpg","type":"r","width":510},{"height":75,"url":"https:\/\/sun7-8.userapi.com\/tatxTPkKwtK_UZfJQEoEfKks_euhabmjytqMjA\/3uPUOVbpCgs.jpg","type":"s","width":75},{"height":604,"url":"https:\/\/sun7-9.userapi.com\/pcjHwXc4CdVglAPwVXXfzZF97hURf57nZnJisA\/6QN_TQqp_5A.jpg","type":"x","width":604},{"height":807,"url":"https:\/\/sun7-6.userapi.com\/e2jl1wAZaJXt6_VNprs-4EQwSRhzUakdqTuIUQ\/w3i94CaFIXo.jpg","type":"y","width":807},{"height":1080,"url":"https:\/\/sun7-9.userapi.com\/QQs8ZVZQSg8jj711HOzWo0E9OLYUqnK6dr0TYA\/vX5oPNtDcAw.jpg","type":"z","width":1080}],"text":"","user_id":100}},{"type":"doc","doc":{"id":570316662,"owner_id":255577237,"title":"Daniel Deogun, Dan Bergh Johnsson, Daniel Sawano - Secure By Design (2019, Manning Publications) - libgen.lc.pdf","size":9555802,"ext":"pdf","date":1601747582,"type":1,"url":"https:\/\/vk.com\/doc255577237_570316662?hash=5c1a212b729f0ce1df&dl=GEYDCMJTHA4TSNQ:1601825627:450be740e58fac29c5&api=1&no_preview=1&module=feed","access_key":"1d074d39b3f47f9443"}}],"post_source":{"type":"vk"},"comments":{"count":0,"can_post":1,"groups_can_post":true},"likes":{"count":50,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":5,"user_reposted":0},"views":{"count":9851},"is_favorite":false,"post_id":306762,"type":"post"},{"source_id":-54530371,"date":1601802120,"can_doubt_category":false,"can_set_category":false,"topic_id":7,"post_type":"post","text":"⌛ Правило трех лет: как сохранить мотивацию на долгосрочных проектах\n \nСохранение творческого запала в течение длительного времени – одна из основных и тяжелых задач, стоящих перед разработчиком. Копнем глубже.\n\nhttps:\/\/proglib.io\/sh\/NpFCThzsjf","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601571997,"id":457268775,"owner_id":-54530371,"has_tags":false,"access_key":"05ea8f7e160081450d","post_id":306749,"sizes":[{"height":130,"url":"https:\/\/sun7-9.userapi.com\/IFe7K93sztUToK6Y0keT7DhAMYL4gbb74nFyow\/SQcke7uV27E.jpg","type":"m","width":130},{"height":130,"url":"https:\/\/sun7-9.userapi.com\/IFe7K93sztUToK6Y0keT7DhAMYL4gbb74nFyow\/SQcke7uV27E.jpg","type":"o","width":130},{"height":200,"url":"https:\/\/sun7-9.userapi.com\/wt_9_eEGEZyLbcnDhViCTi3H58dC2JpKNvqHWA\/ECxO9BtrI6g.jpg","type":"p","width":200},{"height":320,"url":"https:\/\/sun7-6.userapi.com\/16r_D54HAHIRviTtqt9V_OyywQf5HgwJrSgBNQ\/AVbB7sjRfFg.jpg","type":"q","width":320},{"height":510,"url":"https:\/\/sun7-7.userapi.com\/9dl5QETCIYhkAH1KasV9WJXK8QMDmypc0MtdCQ\/K1L5xj-e46I.jpg","type":"r","width":510},{"height":75,"url":"https:\/\/sun7-8.userapi.com\/rVIe1P0_rIHGsFGwkndaVNJKGd0IOMN0eh33WA\/WYS52I8YJ5Y.jpg","type":"s","width":75},{"height":604,"url":"https:\/\/sun7-9.userapi.com\/Jp9w3iwSpx-osBPi4kiTIUszT4TJyP6mnAw3Nw\/IKbUPKz16Yc.jpg","type":"x","width":604}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":5,"can_post":1,"groups_can_post":true},"likes":{"count":115,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":9,"user_reposted":0},"views":{"count":16640},"is_favorite":false,"post_id":306749,"type":"post"},{"source_id":-179664673,"date":1601798401,"can_doubt_category":false,"can_set_category":false,"topic_id":21,"post_type":"post","text":"Первый коммерческий самолёт на водороде \n\nТопливо будущего в самолётах \n \n😱 Что случилось: совершил полёт первый пассажирский самолёт на водородных топливных элементах. Следующий этап — полёт на расстояние 250 миль, это как расстояние от Лондона до Эдинбурга. Если всё получится, то к 2050 году все британские самолёты будут летать на экологичном топливе. \n\nГде-то плачет одна нефтяная вышка. \n\n✈ Кто: ZeroAvia.\n\n#новости_код","marked_as_ads":0,"attachments":[{"type":"video","video":{"access_key":"f2612783c7689da4e6","can_comment":1,"can_like":1,"can_repost":1,"can_subscribe":1,"can_add_to_faves":1,"can_add":1,"comments":0,"date":1601539434,"description":"","duration":59,"image":[{"height":96,"url":"https:\/\/sun7-7.userapi.com\/YFjkAQO2uaC9sbKhSZ_tTeuxBMGZHRn88EzxcQ\/UI2xIGRuYwc.jpg","width":130,"with_padding":1},{"height":120,"url":"https:\/\/sun7-8.userapi.com\/HcKcFaUS3b9asYw6hF-dhAIEiJD4Lk6AMbtVDw\/b0BVGmGGgJk.jpg","width":160,"with_padding":1},{"height":240,"url":"https:\/\/sun7-7.userapi.com\/u2GtlAuNbSd2PWh1QJubRjaQc5REMZz4w9wKIQ\/xJhdhfTw7NE.jpg","width":320,"with_padding":1},{"height":450,"url":"https:\/\/sun7-7.userapi.com\/u3aDCvD8u-oqlMMgAwrfcR_AEqSi7gyx5gWwRw\/RgwHZfXcXBI.jpg","width":800,"with_padding":1},{"height":720,"url":"https:\/\/sun7-6.userapi.com\/vz68PltiSeKdQGPvQtKAqIt8EG1n192S4sboRg\/yfecqo_sT10.jpg","width":720},{"height":320,"url":"https:\/\/sun7-8.userapi.com\/d6ao5pZvw8Zmh3eBU5squ2Pr2g58bsIbzyrtLA\/TX62Epo-2_U.jpg","width":320},{"height":720,"url":"https:\/\/sun7-6.userapi.com\/vz68PltiSeKdQGPvQtKAqIt8EG1n192S4sboRg\/yfecqo_sT10.jpg","width":720},{"height":1024,"url":"https:\/\/sun7-8.userapi.com\/5w0cI3WzYHCmJGWVu0hC1ffXY_zXr5Tuxz5nxg\/5E8BFM3tbuY.jpg","width":1024},{"height":4096,"url":"https:\/\/sun7-9.userapi.com\/Sw3yP4oyLgFnHn5VRKqKfsxZxe3RINAXLFb8ig\/CziW9P2zq04.jpg","width":4096}],"first_frame":[{"height":320,"url":"https:\/\/sun7-7.userapi.com\/xZfHnIoJAcm2yrVFI4dlWJT78ZO402cM4zisYg\/VPkphwMSwbg.jpg","width":320},{"height":160,"url":"https:\/\/sun7-8.userapi.com\/BfO0prLwhgNSGJ08eFlofQVOLiMPOR7cUuGHtg\/HTeVKqXbqII.jpg","width":160},{"height":4096,"url":"https:\/\/sun7-6.userapi.com\/k1oW5KKOxPq9XacQB6hCAbJTYpfOsNI-a0hVsw\/Wsx3ooZYY-M.jpg","width":4096},{"height":130,"url":"https:\/\/sun7-7.userapi.com\/hMZGRbpdqQBZ_d8L4LuDqqI6Lqj-WepHtPBZNQ\/HJN-LE9xeDI.jpg","width":130},{"height":320,"url":"https:\/\/sun7-7.userapi.com\/xZfHnIoJAcm2yrVFI4dlWJT78ZO402cM4zisYg\/VPkphwMSwbg.jpg","width":320},{"height":720,"url":"https:\/\/sun7-6.userapi.com\/2cJ_ek20O9xD4ef8NsAOkapokEb313cGD5UZtQ\/YcL4SgvzAik.jpg","width":720},{"height":1024,"url":"https:\/\/sun7-9.userapi.com\/lO1yC0mN8gAYjNtoqJLxnYb2NrdTT8YXUDgtXA\/tFz9jC7ShCM.jpg","width":1024},{"height":1280,"url":"https:\/\/sun7-7.userapi.com\/mlZdqbzHnkui1lZ6a6EzhhjVtnaFQ4PwlCgKKw\/YimJhPpb_vc.jpg","width":1280},{"height":800,"url":"https:\/\/sun7-9.userapi.com\/Yw_uNLkj273FHHKl6TIQ5ZTsKWgKLL6ejNechg\/GJ0XL2Au9xg.jpg","width":800}],"width":850,"height":850,"id":456239550,"owner_id":-179664673,"title":"Первый коммерческий самолёт на водороде","is_favorite":false,"track_code":"video_d7695476vqrFYX-igyd4rqgQno1ZHlo9LLOPsbECzF_48oN53PaKiNxmf6jqJX6upxV3MeTYbg4VhrqBtmSpOpzirg","repeat":1,"type":"video","views":6229}}],"post_source":{"type":"vk"},"comments":{"count":18,"can_post":1,"groups_can_post":true},"likes":{"count":30,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":3,"user_reposted":0},"views":{"count":7128},"copyright":{"link":"https:\/\/markets.businessinsider.com\/news\/stocks\/zeroavia-completes-world-first-hydrogen-electric-passenger-plane-flight-1029621406","type":"external_link","name":"markets.businessinsider.com"},"is_favorite":false,"post_id":42686,"type":"post"},{"source_id":-186600747,"date":1601794806,"can_doubt_category":false,"can_set_category":false,"post_type":"post","text":"","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601740139,"id":457240635,"owner_id":-186600747,"has_tags":false,"access_key":"8016e85985b7199dfa","post_id":3215,"sizes":[{"height":87,"url":"https:\/\/sun7-9.userapi.com\/ZvivpIguQan-2w3WBJSHCDBlo_NoWpI8OFtRxA\/LPMojrJm2y4.jpg","type":"m","width":130},{"height":87,"url":"https:\/\/sun7-9.userapi.com\/ZvivpIguQan-2w3WBJSHCDBlo_NoWpI8OFtRxA\/LPMojrJm2y4.jpg","type":"o","width":130},{"height":134,"url":"https:\/\/sun7-9.userapi.com\/aIOeZixb1pYM0Q5y5e8-BujZSmVqQTIlIlw0ag\/JieLWJusJmI.jpg","type":"p","width":200},{"height":214,"url":"https:\/\/sun7-8.userapi.com\/0tN-MXjxG3_AesSocxq4qFSgkIqZkVnYv4jqoA\/nBNhsOhKzic.jpg","type":"q","width":320},{"height":341,"url":"https:\/\/sun7-9.userapi.com\/v5C6rXisKkI7R6uBkeEcsJzz2wMaPPwwmAftaw\/7ab9NcWsSGA.jpg","type":"r","width":510},{"height":50,"url":"https:\/\/sun7-8.userapi.com\/0xCuvbjagovPqQ7ebkJp68yVO7TN6HnqiCcHPQ\/nqh9W44fY9U.jpg","type":"s","width":75},{"height":403,"url":"https:\/\/sun7-8.userapi.com\/9ReojSm6uSWIxqoqOmZ_dcS3vjvVHAEeyk5Aqw\/IOUBL9nskXw.jpg","type":"x","width":604},{"height":539,"url":"https:\/\/sun7-8.userapi.com\/QwUcJlwfb6olj0yLwXn8jpAHyq2VYn6URG_Rmw\/kpFOcQhT9EM.jpg","type":"y","width":807},{"height":850,"url":"https:\/\/sun7-7.userapi.com\/L9oHhnnzErrKaAULfFk4wHEfg9eNQ_tg5OUucw\/LRPuYcbKImk.jpg","type":"z","width":1273}],"text":"","user_id":100}}],"post_source":{"type":"api","platform":"android"},"comments":{"count":4,"can_post":1,"groups_can_post":true},"likes":{"count":161,"user_likes":1,"can_like":0,"can_publish":1},"reposts":{"count":0,"user_reposted":0},"views":{"count":2612},"is_favorite":false,"post_id":3218,"type":"post"},{"source_id":-54530371,"date":1601741100,"can_doubt_category":false,"can_set_category":false,"category_action":{"action":{"target":"internal","type":"open_url","url":"vk.com\/feed?section=custom&feed_id=discover_category\/7"},"name":"IT"},"topic_id":7,"post_type":"post","text":"Сайт на C++ своими руками с помощью библиотеки cgicc\n\nРассказываем об основах создания простого бэкенда для сайта на обычном шаред-хостинге с помощью языка C++ и библиотеки cgicc. \n \nhttps:\/\/proglib.io\/w\/02e8c5fe","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601653090,"id":457268783,"owner_id":-54530371,"has_tags":false,"access_key":"6d5138a492e2959f32","post_id":306656,"sizes":[{"height":105,"url":"https:\/\/sun7-7.userapi.com\/4MMG3Gd6WtMw8GceW4zZn42JOYjNQHuOL75xFA\/-P624GmUf8Q.jpg","type":"m","width":130},{"height":105,"url":"https:\/\/sun7-7.userapi.com\/4MMG3Gd6WtMw8GceW4zZn42JOYjNQHuOL75xFA\/-P624GmUf8Q.jpg","type":"o","width":130},{"height":161,"url":"https:\/\/sun7-9.userapi.com\/9bSbIQKF94l2jJYNeZt0Lwpec4MLmXOdziNahg\/LYvPOWKoFUI.jpg","type":"p","width":200},{"height":258,"url":"https:\/\/sun7-9.userapi.com\/5fQLpE2cLEOeIlSY_5TKSjsYI9ER5bgy8yAlEQ\/EqOny89lMb4.jpg","type":"q","width":320},{"height":411,"url":"https:\/\/sun7-9.userapi.com\/HZ6xTD32xO_8HaQrjWbX9R0jOJxBc8uTFwhkxg\/Hh4oN89ap_0.jpg","type":"r","width":510},{"height":61,"url":"https:\/\/sun7-7.userapi.com\/G6ob9n_N6FcAeTk96did6vtrMEFAFBiNZFz4dQ\/VF6Znz5T8Gc.jpg","type":"s","width":75},{"height":487,"url":"https:\/\/sun7-9.userapi.com\/Gwe5b7jbpGoRraXjz_5YwMjMM7ghlZX3PjB_mg\/UDpGFodvDf0.jpg","type":"x","width":604},{"height":651,"url":"https:\/\/sun7-9.userapi.com\/hUZMqK44SPgBE_ypVPcliTjgCBLsMJBR3V3oJw\/nOo-PZ2H9c8.jpg","type":"y","width":807},{"height":1028,"url":"https:\/\/sun7-8.userapi.com\/kVEhZMZOgIG1nrzw3hQxydqspfJexbf18nz-pQ\/k-yvTbKE5FM.jpg","type":"z","width":1275}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":41,"can_post":1,"groups_can_post":true},"likes":{"count":234,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":15,"user_reposted":0},"views":{"count":30567},"is_favorite":false,"post_id":306656,"type":"post"},{"source_id":-54530371,"date":1601737381,"can_doubt_category":false,"can_set_category":false,"category_action":{"action":{"target":"internal","type":"open_url","url":"vk.com\/feed?section=custom&feed_id=discover_category\/7"},"name":"IT"},"topic_id":7,"post_type":"post","text":"📸 Как сделать галерею в стиле Instagram\n\nГалереи картинок с горизонтальной прокруткой выглядят современно и узнаваемо. Рассказываем, как с помощью CSS и JS перенести знакомую пользователям механику в ваши веб-приложения. \n \nhttps:\/\/proglib.io\/w\/9c844462","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601573166,"id":457268776,"owner_id":-54530371,"has_tags":false,"access_key":"7e6cb0ff568daf32e7","post_id":306632,"sizes":[{"height":89,"url":"https:\/\/sun7-9.userapi.com\/nEGMHB4SrZP6BWXn0CC-UJowSTFLl_ZHsZ3Dtw\/XWK6xSIMh94.jpg","type":"m","width":130},{"height":89,"url":"https:\/\/sun7-9.userapi.com\/nEGMHB4SrZP6BWXn0CC-UJowSTFLl_ZHsZ3Dtw\/XWK6xSIMh94.jpg","type":"o","width":130},{"height":137,"url":"https:\/\/sun7-9.userapi.com\/JES0zR_aASmLtFjWlEf1_ckDXBd0av97eZk8zA\/FvbOGc4dyiA.jpg","type":"p","width":200},{"height":219,"url":"https:\/\/sun7-8.userapi.com\/BLXDmDVqgGGGSbiPhwnpg9NP9jN6atQIMvAQ2w\/EZECDChriGc.jpg","type":"q","width":320},{"height":349,"url":"https:\/\/sun7-7.userapi.com\/BcTUH1B3R7YnAP83P2s3zwkAG_ZCswmxPAANrw\/3UPXRx1iS30.jpg","type":"r","width":510},{"height":51,"url":"https:\/\/sun7-6.userapi.com\/dkAd3Bz-OXOmRjHbBLNdQDNQ4neIgM906qqe7w\/zSo1ETaWGzY.jpg","type":"s","width":75},{"height":413,"url":"https:\/\/sun7-7.userapi.com\/gS3Y9EAlp3SQQMApVffrkk7vym3lCPb5Z3KP-Q\/3sVXOnAmbHk.jpg","type":"x","width":604},{"height":547,"url":"https:\/\/sun7-6.userapi.com\/L2Bu1g0zMwSVzf1WojZINw_LH2lLx3H6l0p4sg\/_F3rKACEFpo.jpg","type":"y","width":800}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":4,"can_post":1,"groups_can_post":true},"likes":{"count":145,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":8,"user_reposted":0},"views":{"count":23314},"is_favorite":false,"post_id":306632,"type":"post"},{"source_id":-54530371,"date":1601733660,"can_doubt_category":false,"can_set_category":false,"category_action":{"action":{"target":"internal","type":"open_url","url":"vk.com\/feed?section=custom&feed_id=discover_category\/7"},"name":"IT"},"topic_id":7,"post_type":"post","text":"ТОП-30 наиболее интересных GitHub-проектов для обучения\n\nПредставляем вашему вниманию подборку интересных и красиво оформленных GitHub-проектов, а также полезных туториалов и инструментов. \n \nhttps:\/\/proglib.io\/w\/d8f6f4a5","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601652355,"id":457268782,"owner_id":-54530371,"has_tags":false,"access_key":"01abc2695bf3cafdfd","post_id":306622,"sizes":[{"height":106,"url":"https:\/\/sun7-8.userapi.com\/1t2smdpxIYoxzgDFki25omJp_OcEWp9o-UK8mA\/8W39h1kRJOw.jpg","type":"m","width":130},{"height":106,"url":"https:\/\/sun7-8.userapi.com\/1t2smdpxIYoxzgDFki25omJp_OcEWp9o-UK8mA\/8W39h1kRJOw.jpg","type":"o","width":130},{"height":163,"url":"https:\/\/sun7-7.userapi.com\/MD4_KZeVJfVA07KjsHLej1sAaqTJ-HEg2mNZsA\/ejG3uMdlDi8.jpg","type":"p","width":200},{"height":261,"url":"https:\/\/sun7-9.userapi.com\/VCP4Zl52UMtMQ8YPVohYPXnU1K-qaz5sQUfRfA\/uCEnIXGOjpE.jpg","type":"q","width":320},{"height":415,"url":"https:\/\/sun7-8.userapi.com\/96cWkpaODxznD1QcSeb1m3H7jQbUQLddJp94IA\/0yMBd0RaFHw.jpg","type":"r","width":510},{"height":61,"url":"https:\/\/sun7-9.userapi.com\/71UfDeQtb5tez9CWn1JS4r5z-vSWhN44K31Slg\/13y-XqCHs7g.jpg","type":"s","width":75},{"height":492,"url":"https:\/\/sun7-9.userapi.com\/J8SWZS2o5oilcjYIUgtFefEsTJHqOXbwgNBVkw\/239K7DPH2vg.jpg","type":"x","width":604},{"height":566,"url":"https:\/\/sun7-7.userapi.com\/IGm9WVNREw3M9nN75CwePJRIRgeTnOvJd_d_LQ\/BQ6pn7mBI8g.jpg","type":"y","width":695}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":9,"can_post":1,"groups_can_post":true},"likes":{"count":214,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":16,"user_reposted":0},"views":{"count":34668},"is_favorite":false,"post_id":306622,"type":"post"},{"source_id":-186600747,"date":1601732629,"can_doubt_category":false,"can_set_category":false,"post_type":"post","text":"#информационныйKANст\nЧе вообще по короне в универе? Слишком много инфы приходит: с военки, с первого здания, с пятого здания. Несколько групп на карантин закрыли. Хуй пойми где правда, а где пиздеж... \nПОЭТОМУ КИДАЙТЕ ВСЮ ЗНАЮЩУЮ ИНФОРМАЦИЮ О КОРОНЕ И КОВИД-19 В ЛС ПАБЛИКА С ПРУФАМИ (ПЕРЕПИСКИ, ПРИКАЗЫ, ДОКУМЕНТАЦИЯ И Т.Д.). БУДЕМ ЗАНОСИТЬ В ОТДЕЛЬНЫЙ ПОСТ ВСЮ ИНФОРМАЦИЮ, КОТОРАЯ БУДЕТ У НАС, ЧТОБЫ БЫЛА КАКАЯ-ТО ОБЩАЯ ИНФОРМАЦИОННАЯ БАЗА ПО ЭТОЙ СИТУАЦИИ❗❗❗","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601732629,"id":457240632,"owner_id":-186600747,"has_tags":false,"access_key":"a13aa41b62066f5d02","post_id":3210,"sizes":[{"height":98,"url":"https:\/\/sun7-6.userapi.com\/C78yPEgTnwSQtHVvI-_Ee50DUtppL2YJ2coAyg\/E5HK5u8xjJ0.jpg","type":"m","width":130},{"height":98,"url":"https:\/\/sun7-6.userapi.com\/C78yPEgTnwSQtHVvI-_Ee50DUtppL2YJ2coAyg\/E5HK5u8xjJ0.jpg","type":"o","width":130},{"height":150,"url":"https:\/\/sun7-6.userapi.com\/8H3AZTCjdTuZYxWE8nUgnu939SNkI685YYlLqQ\/Y1_1cw_v-dc.jpg","type":"p","width":200},{"height":240,"url":"https:\/\/sun7-7.userapi.com\/wGDvrwayvYY4INmPsx_JEnWSuk9EzfQ0K29M3g\/yf8fMAzJ9VA.jpg","type":"q","width":320},{"height":383,"url":"https:\/\/sun7-9.userapi.com\/AaQUB5H9U56SzXWP7RAu7y8hWpblb7MWEnQMHQ\/M4fWE9DDL6A.jpg","type":"r","width":510},{"height":57,"url":"https:\/\/sun7-6.userapi.com\/w2x-ReyHAS7eXbheANIfqtTVM5h5lOO4_Uk0Iw\/M69_E6K-NcI.jpg","type":"s","width":75},{"height":454,"url":"https:\/\/sun7-8.userapi.com\/C_-8WqMeNlx8CrJ8EObah0LDiLhiNr0h0-50_w\/tXcd58BgYK4.jpg","type":"x","width":604},{"height":606,"url":"https:\/\/sun7-8.userapi.com\/8gsQ7mIiUbFUz9HL00decOtupXkjqJlSQSzxZw\/Qr-C7j5nRIw.jpg","type":"y","width":807},{"height":957,"url":"https:\/\/sun7-7.userapi.com\/85cE39IzeEMjEMbeBitNdPWP6GRaNEktArR1ag\/n444BO7rVaI.jpg","type":"z","width":1275}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":0,"can_post":0},"likes":{"count":63,"user_likes":1,"can_like":0,"can_publish":1},"reposts":{"count":0,"user_reposted":0},"views":{"count":2604},"is_favorite":false,"post_id":3210,"type":"post"},{"source_id":-54530371,"date":1601730960,"can_doubt_category":false,"can_set_category":false,"topic_id":7,"post_type":"post","text":"📌 Курс математики для анализа данных от Proglib и МГУ \n \nМы предлагаем без отрыва от работы освоить математику для анализа данных. \n✅ онлайн-встречи с преподавателями из МГУ в Zoom \n✅ подойдет всем, кто хочет вспомнить высшую математику или подготовиться к школе анализа данных Яндекса \n🕗 длительность 5 месяцев, по 2 раза в неделю \n💸 от 5 350 руб. в месяц. \n \nМы дарим промокод на скидку 10% до 6 октября — PROGLIB \nПодробнее о курсе — https:\/\/courses.proglib.io\/\nПрограмма курса — https:\/\/courses.proglib.io\/programma","marked_as_ads":0,"attachments":[{"type":"photo","photo":{"album_id":-7,"date":1601723329,"id":457268793,"owner_id":-54530371,"has_tags":false,"access_key":"4c0baafdb0b22e367f","post_id":306615,"sizes":[{"height":130,"url":"https:\/\/sun7-7.userapi.com\/iuRN98xhM7762-IVrFtsCTxt4U89Rp9ZcOLLLg\/4tjeRvTrei0.jpg","type":"m","width":99},{"height":171,"url":"https:\/\/sun7-6.userapi.com\/VjjyfDIA4irP97RJxb-khFrsAXG0Pq62P3MhMA\/avUj_o0RGS8.jpg","type":"o","width":130},{"height":264,"url":"https:\/\/sun7-7.userapi.com\/ur6zIQ3Zdu9BS2sAERId2PlOPLpvWaZwrc1p-A\/zNPXyyOmsHY.jpg","type":"p","width":200},{"height":422,"url":"https:\/\/sun7-7.userapi.com\/1D0GNyDiWE50AuGFL4hZVDVA9U46DVPNeCQapA\/HDSVQzEMmqs.jpg","type":"q","width":320},{"height":673,"url":"https:\/\/sun7-6.userapi.com\/IAqj3B4nc_vi2OinaMHWiRprKz29t2GNeCBvbA\/77ABvHMquMA.jpg","type":"r","width":510},{"height":75,"url":"https:\/\/sun7-6.userapi.com\/7g2arSiyVcj7KXLOZzRTrQ0fwF-Vzdr-5rUdzQ\/lToaskMCQ2o.jpg","type":"s","width":57},{"height":604,"url":"https:\/\/sun7-6.userapi.com\/x68XtW2udrGss_ElBWaMYKkL8_73NewzVtqcqg\/OqdEj7V2Dc4.jpg","type":"x","width":458},{"height":807,"url":"https:\/\/sun7-7.userapi.com\/5OctEa2dqTaN0ny91yR6mkb6cc6LCWuT0Xb1bQ\/93vKFtUIZdU.jpg","type":"y","width":612},{"height":897,"url":"https:\/\/sun7-6.userapi.com\/wc5TMA1W337gTMwNhIK-BaVfq0hQFwvKz93aiQ\/cCZoWroSIwY.jpg","type":"z","width":680}],"text":"","user_id":100}}],"post_source":{"type":"vk"},"comments":{"count":7,"can_post":1,"groups_can_post":true},"likes":{"count":200,"user_likes":0,"can_like":1,"can_publish":1},"reposts":{"count":9,"user_reposted":0},"views":{"count":28221},"is_favorite":false,"post_id":306615,"type":"post"}],"profiles":[{"id":100,"first_name":"Администрация ВКонтакте","last_name":"","is_closed":false,"can_access_closed":true,"sex":1,"screen_name":"id100","photo_50":"https:\/\/sun7-9.userapi.com\/impf\/c847124\/v847124728\/335f4\/thh2-8S3ZKM.jpg?size=50x0&quality=88&crop=0,0,400,400&sign=658829254def89878c70812be46c5b17&c_uniq_tag=oN9FENR3lUfh_TW4GYODqGnfv8gnmVWyKnwzBfES0HE&ava=1","photo_100":"https:\/\/sun7-9.userapi.com\/impf\/c847124\/v847124728\/335f4\/thh2-8S3ZKM.jpg?size=100x0&quality=88&crop=0,0,400,400&sign=9d447ea0df27e8bef0548259cb021a9c&c_uniq_tag=DoYZr3U2rDoJSyK9sxS3c5gFGzlBfoPsRfef2P3L2M8&ava=1","online":0,"online_info":{"visible":true,"is_online":false,"is_mobile":false}},{"id":255577237,"first_name":"Влад","last_name":"Иванов","is_closed":false,"can_access_closed":true,"sex":2,"screen_name":"senyalovesyou","photo_50":"https:\/\/sun7-8.userapi.com\/impg\/rE1sBodHEUrUAXP-3mN7p7Mer_wxEVTVHzC72A\/5_Tacc29TSk.jpg?size=50x0&quality=88&crop=0,7,448,448&sign=5c286645d1901a819f1d3d5f94cb5f61&c_uniq_tag=dLGwEaAXKSBsA0ujhQirT2rQpGdlu2-Oc8wNvBjSP1I&ava=1","photo_100":"https:\/\/sun7-8.userapi.com\/impg\/rE1sBodHEUrUAXP-3mN7p7Mer_wxEVTVHzC72A\/5_Tacc29TSk.jpg?size=100x0&quality=88&crop=0,7,448,448&sign=19d62a4d6e5fb25035585814c8a4381e&c_uniq_tag=8HwuXqKuGDhcQFGMSO_HrhOf4_r9RN78js3_4DCU9yQ&ava=1","online":1,"online_app":2274003,"online_mobile":1,"online_info":{"visible":true,"last_seen":1601825614,"is_online":true,"app_id":2274003,"is_mobile":true}},{"id":570316662,"first_name":"Константин","last_name":"Доронин","is_closed":false,"can_access_closed":true,"sex":2,"screen_name":"id570316662","photo_50":"https:\/\/vk.com\/images\/camera_50.png?ava=1","photo_100":"https:\/\/vk.com\/images\/camera_100.png?ava=1","online":0,"online_info":{"visible":true,"last_seen":1601733935,"is_online":false,"app_id":2274003,"is_mobile":true}},{"id":570316930,"first_name":"Жека","last_name":"Коромщиков","is_closed":false,"can_access_closed":true,"sex":2,"screen_name":"id570316930","photo_50":"https:\/\/sun7-7.userapi.com\/impf\/c855024\/v855024542\/1696e0\/pfZGW8lylUE.jpg?size=50x0&quality=88&crop=0,269,1620,1620&sign=691937de2691cff38f5f5f82d62e48b4&c_uniq_tag=Lw4ssy_IaPAAnhF5eohLHmat3QlJ6rqfitbCqdLsIS8&ava=1","photo_100":"https:\/\/sun7-7.userapi.com\/impf\/c855024\/v855024542\/1696e0\/pfZGW8lylUE.jpg?size=100x0&quality=88&crop=0,269,1620,1620&sign=88a471d02000d4050580eb0299438df1&c_uniq_tag=8R2tfWeb9Sh-I9iOA62WwpurgRbyuCS20VaiSFESiSM&ava=1","online":0,"online_info":{"visible":true,"last_seen":1577213249,"is_online":false,"is_mobile":true}}],"groups":[{"id":54530371,"name":"Библиотека программиста","screen_name":"proglib","is_closed":0,"type":"page","photo_50":"https:\/\/sun7-7.userapi.com\/impf\/c858132\/v858132625\/be932\/AYLhLtwcWxY.jpg?size=50x0&quality=88&crop=122,13,1090,1090&sign=2ac8d8e2b0da6021b0f9a10d30882aa0&c_uniq_tag=fq7udfXnmYZLvLA-SsGUO248-BYsHuos2VKShin1dfE&ava=1","photo_100":"https:\/\/sun7-7.userapi.com\/impf\/c858132\/v858132625\/be932\/AYLhLtwcWxY.jpg?size=100x0&quality=88&crop=122,13,1090,1090&sign=804a922f23cda4426070dd2418422b56&c_uniq_tag=2ediTNbvgINv1UgWsgqZ09VMQcvPq51o1ovX5lIvmog&ava=1","photo_200":"https:\/\/sun7-7.userapi.com\/impf\/c858132\/v858132625\/be932\/AYLhLtwcWxY.jpg?size=200x0&quality=88&crop=122,13,1090,1090&sign=bfe8d4c29b833f21c5631cac2ccb70cd&c_uniq_tag=5uT6Yvs6lxxs-qU6VbODuRbSE-hSGw4D9teNZzsK27w&ava=1"},{"id":179664673,"name":"Журнал «Код»","screen_name":"thecode.media","is_closed":0,"type":"page","photo_50":"https:\/\/sun7-9.userapi.com\/impg\/c855620\/v855620212\/160de2\/IWBHISdTflQ.jpg?size=50x0&quality=88&crop=235,222,613,613&sign=b65cc10607ba2beb44fc4cab96d51dac&c_uniq_tag=ov1p1U7JGodISN5l4gGgrx2z79w6TfJajzis_GswMYw&ava=1","photo_100":"https:\/\/sun7-9.userapi.com\/impg\/c855620\/v855620212\/160de2\/IWBHISdTflQ.jpg?size=100x0&quality=88&crop=235,222,613,613&sign=0633ba8c131af727d5f2c27556ad8ed2&c_uniq_tag=O0KSKC_KR-DF9XBY6YbpAPx-qxADq1KJH-HQG0iXQ5M&ava=1","photo_200":"https:\/\/sun7-9.userapi.com\/impg\/c855620\/v855620212\/160de2\/IWBHISdTflQ.jpg?size=200x0&quality=88&crop=235,222,613,613&sign=e9580ddb9bd2d4c2ece5a8c141112f5e&c_uniq_tag=FAAg9cJaCcMDDEenQik4eOR0afS1U1ZdRxwR_u5Rfgo&ava=1"},{"id":186600747,"name":"KANст","screen_name":"kachi_club","is_closed":0,"type":"page","photo_50":"https:\/\/sun7-6.userapi.com\/impg\/c857632\/v857632778\/1ab193\/T8UR6xjpfgU.jpg?size=50x0&quality=88&crop=0,0,1275,1275&sign=7e6eedf3a829f981c057e53ef181471c&c_uniq_tag=lMD72gbAiLiuG9AmlvbyjfeCHre7LYzayEFfxKrt60w&ava=1","photo_100":"https:\/\/sun7-6.userapi.com\/impg\/c857632\/v857632778\/1ab193\/T8UR6xjpfgU.jpg?size=100x0&quality=88&crop=0,0,1275,1275&sign=e19c473c041782ed70b8cb95924a2f34&c_uniq_tag=wTEcDx3Yjf1abX78tkiC2fX4iOTIwj4OV4DWLtqCs0k&ava=1","photo_200":"https:\/\/sun7-6.userapi.com\/impg\/c857632\/v857632778\/1ab193\/T8UR6xjpfgU.jpg?size=200x0&quality=88&crop=0,0,1275,1275&sign=edbfef30f824053ddb2932e4aa644eaf&c_uniq_tag=hUy7CHaRvnOrNPisECpHBT8JXucmcYxXgyU4pQQdXng&ava=1"}],"next_from":"10\/5_-54530371_306615:1845625910"}}"""