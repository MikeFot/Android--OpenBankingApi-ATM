

-keepattributes InnerClasses

-keep class **.R
-keep class **.R$* {
    <fields>;
}



-keepattributes *Annotation*
-keep class com.google.inject.** { *; } 
-keep class javax.inject.** { *; } 
-keep class javax.annotation.** { *; } 
-keep class roboguice.** { *; } 



