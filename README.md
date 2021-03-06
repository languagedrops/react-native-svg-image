
# react-native-svg-image

## Getting started

`$ yarn add languagedrops/react-native-svg-image`

### Mostly automatic installation

`$ react-native link react-native-svg-image`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-svg-image` and add `RNSvgImage.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSvgImage.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.languagedrops.rnsvg.SvgImagePackage;` to the imports at the top of the file
  - Add `new SvgImagePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-svg-image'
  	project(':react-native-svg-image').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-svg-image/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-svg-image')
  	```


## Usage
```javascript
import SvgImageView from 'react-native-svg-image';

const Svg = <SvgImageView src={resName} style={style} tintColor={tintColor}/>
```
  
