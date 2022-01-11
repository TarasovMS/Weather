//package com.tarasovms.weather.base
//
//import android.content.Context
//import android.graphics.Typeface
//
//
//class Fonts() {
//
//    val font1: Typeface?
//        get() = Companion.font1
//    val font2: Typeface?
//        get() = Companion.font2
//    val font3: Typeface?
//        get() = Companion.font3
//
//    companion object {
//        private var font1: Typeface? = null
//        private var font2: Typeface? = null
//        private var font3: Typeface? = null
//
//        private fun setFont1(font1: Typeface?) {
//            Companion.font1 = font1
//        }
//
//        fun setFont2(font2: Typeface?) {
//            Companion.font2 = font2
//        }
//
//        private fun setFont3(font3: Typeface?) {
//            Companion.font3 = font3
//        }
//
//        @Volatile
//        private var instance: Fonts? = null
//        fun getInstance(context: Context): Fonts? {
//            var localInstance = instance
//            if (localInstance == null) {
//                synchronized(Fonts::class.java) {
//                    localInstance = instance
//                    if (localInstance == null) {
//                        localInstance = Fonts()
//                        instance = localInstance
//                    }
//                }
//                setFont1(Typeface.createFromAsset(context.assets, "fonts/jakobctt-bold.ttf"))
//                setFont2(Typeface.createFromAsset(context.assets, "fonts/jakobtt-bold.ttf"))
//                setFont3(Typeface.createFromAsset(context.getAssets(), "fonts/lazurski-italic-cyrillic.ttf"))
//            }
//            return localInstance
//        }
//    }
//
//}