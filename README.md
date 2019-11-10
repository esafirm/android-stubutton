## android-stubutton
Simple Slide to Unlock Button 

![Demo](https://raw.githubusercontent.com/esafirm/android-stubutton/master/art/out.gif)

## Download
You can just copy right away the required files (it's only 1 class!)

### Gradle

In your project `build.gradle`

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

In your module `build.gradle`

```groovy
dependencies {
	implementation 'com.github.esafirm:android-stubutton:1.1.0'
}
```

## Setup
```xml
<com.esafirm.stubutton.StuButton
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:stu_label="No Money"
        app:stu_thumbDrawable="@drawable/circle_material"
        app:stu_background="@android:color/black"/>
```

Complete example is in the sample

## License
Esa Firman @ [MIT](https://raw.githubusercontent.com/esafirm/android-stubutton/master/LICENSE)



