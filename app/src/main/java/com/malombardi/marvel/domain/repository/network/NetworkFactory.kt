package com.malombardi.marvel.domain.repository.network

import com.malombardi.marvel.BuildConfig
import com.malombardi.marvel.domain.Constants.LIMIT_KEY
import com.malombardi.marvel.domain.Constants.OFFSET_KEY
import com.malombardi.marvel.domain.Constants.PAGE_SIZE
import com.malombardi.marvel.domain.Constants.STARTING_OFFSET
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class NetworkFactory {
    companion object {
        private const val ORDER_BY_KEY = "orderBy"
        private const val ORDER_BY_TYPE = "name"
        private const val TS_KEY = "ts"
        private const val API_KEY = "apikey"
        private const val HASH_KEY = "hash"
        private const val HASH_ALGO_TYPE = "MD5"

        fun getNetworkOptions(offset: Int = STARTING_OFFSET): Map<String, String> {
            val ts = System.currentTimeMillis().toString()
            val options = mutableMapOf<String, String>()
            //options[ORDER_BY_KEY] = ORDER_BY_TYPE
            options[TS_KEY] = ts
            options[LIMIT_KEY] = PAGE_SIZE.toString()
            options[API_KEY] = BuildConfig.public_key
            options[HASH_KEY] = generateHash(ts + BuildConfig.private_key + BuildConfig.public_key)
            options[OFFSET_KEY] = offset.toString()

            return options
        }

        //I found this on internet, I didn't know how to make a MD5 before this
        private fun generateHash(s: String): String {
            try {
                val digest: MessageDigest = MessageDigest
                    .getInstance(HASH_ALGO_TYPE)
                digest.update(s.toByteArray())
                val messageDigest: ByteArray = digest.digest()

                val hexString = StringBuffer()
                for (i in messageDigest.indices) {
                    var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}
