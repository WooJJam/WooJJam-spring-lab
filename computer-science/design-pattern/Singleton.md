## 싱글톤 패턴

Subject: 디자인패턴

디자인 패턴이란 객체 지향 설계를 진행할 때 발생하는 반복적인 문제들을 어떻게 해결할 것 인지에 대한 해결방안으로 많은 사람들이 인정한 모범 사례다.

### 싱글톤 패턴

하나의 클래스에 오직 하나의 인스턴스만 가지는 패턴이다. 즉, 생성자가 여러번 호출되도라도 실제로 생성되는 객체는 오직 하나이다. 보통 데이터베이스 커넥션 풀에서 많이 사용된다.

이러한 특징으로 인해 인스턴스 생성 비용이 줄어든다는 장점은 있지만 의존성이 높아진다는 단점도 존재한다.

### 장점

1. 메모리 측면
    - 당연하게도 생성되는 인스턴스가 1개이므로 고정 메모리 여역에 생성하여 메모리 낭비를 방지할 수 있음
2. 속도 측면
    - 이미 생성된 인스턴스를 활용하기에 이점이 있음
3. 공유 측면
    1. 전역으로 사용하는 인스턴스이므로 여러 클래스에서 데이터를 공유하며 사용할 수 있지만.. 공유 데이터이므로 동시성 문제를 주의해야 함

### 자바에서의 싱글톤

#### Eager Initialization

```java
class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

가장 직관적이면서 심플한 기법으로 한번만 미리 만들어두는 방식이다. `static final` 이므로 클래스 초기화 시점에 초기화 되므로 멀티 쓰레드 환경에서도 안전하다. 

하지만 정적 변수는 당장 객체를 사용하지 않아도 메모리에 적재되게 때문에 리소스가 큰 객체일 수록 결국 **메모리 낭비**가 발생할 수 있다.

또한 예외가 발생하더라도 예외를 처리할 방법이 없다는 단점이 있다.

#### Static block Initialization

```java
class Singleton {

    private static Singleton instance;

    private Singleton() {}
    

    static {
        try {
            instance = new Singleton();
        } catch (Exception e) {
            throw new RuntimeException("싱글톤 객체 생성시 오류 발생");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }
}
```

static block을 이용하여 객체 생성시 예외를 처리할 수 있다. 하지만 여전히 static의 특성으로 인해 사용하지 않더라도 공간을 차지한다.

#### Lazy initialization

```java
class Singleton {

    private static Singleton instance;

    private Singleton() {}
	
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

많은 책에서 소개하는 싱글톤 방식으로 약간 정석(?)처럼 사용되는 것 같다.

객체 생성을 `getInstance()` 메소드 내에서 관리하며 메서드를 호출했을때 null 유무에 따라 초기화하거나 반환하는 방식이다. 메소드 호출시에 초기화되기 때문에 앞서 언급한 사용하지 않을 경우 발생하는 고정 메모리 낭비 문제점을 해결할 수 있다.

하지만 멀티 스레드 환경에서 `getInstance` 를 동시에 호출하게 될 경우 인스턴스가 여러개 생성될 수 있다. 즉, 해당 방식은 Thread-Safe 하지 않다는 치명적인 단점을 가지고 있다.

#### Thread safe initialization

```java
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

`synchronized` 키워드를 통해 메서드에 쓰레드들을 하나씩 접근하게 하도록 설정하여 thread-safe를 보장할 수는 있다. 

하지만 `Synchronized` 의 특성상 메서드를 매번 호출할 경우 오버헤드가 발생해 성능 하락으로 이어지게 된다.

**Bill Pugh Solution (LazyHolder)**

```java
class Singleton {

    private Singleton() {}

    private static class SingleInstanceHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }
}
```

가장 권장되는 방식이다. 멀티쓰레드 환경에서도 안전하고, Lazy Loding도 가능한 완벽한 싱글톤 기법이다.

클래스안에 `holder` 라는 내부 클래스를 두어 JVM의 클래스 로더 방식과 클래스가 로드되는 시점을 이용한 방법이다.

<aside>
📌

1. 내부 클래스가 static 이므로 싱글톤 클래스가 초기화 되더라도 `SingleInstanceHolder` 내부 클래스는 메모리에 로드되지 않음
2. `getInstance()` 를 호출할 때, `SingleInstanceHolder` 내부 클래스의 `static` 변수를 가져와 리턴하게 되는데, 이때 싱글톤 객체를 최초로 생성 및 반환하게 됨
3. `final` 로 지정되어 있어 값이 재활당되지 않도록 방지함
</aside>

코드의 동작 흐름은 다음과 같은데 해당 내용은 JVM의 Class Loader가 어떻게 클래스를 로딩하고 초기화하는지에 대해서 알고 있어야 한다. 해당 내용은 추후 정리해보고 수정해야지,,

### 싱글톤은 안티패턴?

말만 들었을때 싱글톤은 고정된 메모리 영역에서 하나의 인스턴스만을 사용하기 때문에 메모리 낭비를 방지할 수 있기에 상당히 큰 장점으로 보인다. 하지만 실제로는 많은 문제점들을 수반하고 있다.

### 문제점

1. **의존성 증가**
    - 싱글톤의 특성상 클래스의 객체를 미리 생성하고, 정적 메소드를 사용하기 때문에 모듈들끼리 의존성이 강해질 수 밖에 없음
        
        <aside>
        📌
        
        의존성이란 종속성이라고도 하며, A가 B에 의존성이 있다는 것은 B의 변경 사항에 대해 A도 변경되어야 한다는 것을 의미한다.
        
        </aside>
        
    - 하나의 싱글톤 클래스를 여러 모듈이 공유하므로, 해당 인스턴스가 변경되면 이를 참조하는 모든 모듈들을 수정해야함

1. **SOLID 원칙 위배**
    - 한 개의 인스턴스만 사용하므로 여러가지 책임이 동반될수록 `SRP` 위반
    - 확장이 사실상 불가능 하기에 `OCP` 위반
    - 인터페이스가 아닌 구체 클래스에 의존하므로 `DIP` 원칙 위반
2. **TDD 단위 테스트**
    - 단위 테스트는 테스트가 서로 독립적으로 이루어져야 하는데.. 싱글톤 패턴은 1개의 인스턴스를 사용하므로 **독립적인** 테스트를 만들기 어렵다.
3. **상속 불가능**
    - 외부에서 객체를 생성하지 못하도록 하기 위해서 생성자를 private로 설정하므로 상속이 불가능함

이처럼 싱글톤 패턴은 장점도 있지만 실제로 유연성이 많이 떨어지는 패턴이라 안티 패턴으로 분류되기도 한다.

스프링 프레임워크의 경우 클래스의 제어를 `IOC` 방식의 컨테이너에게 넘겨 스프링 컨테이너가 관리하므로 싱글톤의 문제점을 극복하고 사용할 수 있다. 해당 내용도 추후 정리해서 업데이트 해보겠다.

하지만 만약 개발자가 직접 싱글톤을 구축하고, 사용하고자 한다면 앞서 살펴본 장 단점을 잘 고려하여야 할 것 이다.
