
# 指定代码的压缩级别
#-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 不压缩
#-dontshrink

## 关闭优化
-dontoptimize


#-keep interface com.mss.sdk.wrapper.** {*;}
#-keep interface com.mss.sdk.listener.** {*;}
#-keep interface com.openup.sdk.MsSDK$OpenUpEuropeanUnionUserCheckCallBack{*;}
#-keep interface com.openup.sdk.MsSDK$OpenUpAccessPrivacyInfoStatusCallBack{*;}


# gms adapter
-keep class com.google.ads.mediation.** {*;}
-keep class com.jirbo.adcolony.** {*;}
-keep class com.vungle.mediation.** {*;}
#-dontwarn com.vungle.mediation.**
#-dontwarn com.google.ads.mediation.**


#-dontwarn
-keepclasseswithmembernames class * {
    native <methods>;
}

# support
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Activity
-keep class android.support.** {*;}
-keep class com.google.gson.** {*;}
-dontwarn android.support.**



## ironsrc-start
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

## ironsrc-end

## mopub-start

# Keep public classes and methods.
-keepclassmembers class com.mopub.** { public *; }
-keep public class com.mopub.**
-keep public class android.webkit.JavascriptInterface {}

# Explicitly keep any BaseAd and CustomEventNative classes in any package.
-keep class * extends com.mopub.mobileads.BaseAd {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}

# Keep methods that are accessed via reflection
-keepclassmembers class ** { @com.mopub.common.util.ReflectionTarget *; }

# Support for Android Advertiser ID.
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}

# Support for Google Play Services
# http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

## mopub-end


### gusdk-start
#-keepclassmembers class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
#-keep class com.gu.sdk.** { *;}
#-keep interface com.gu.sdk.** {*;}
#-keep enum com.gu.sdk.** {*;}
#-keep class com.gu.sdk.manager.**{*;}
#-keep class com.gusdk.sdk.** {*;}
#-keep interface com.gusdk.** {*;}
#-keep com.gusdk.mobileads.** {*;}
#-keepattributes JavascriptInterface
### max-end