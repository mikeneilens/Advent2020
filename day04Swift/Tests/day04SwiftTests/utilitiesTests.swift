import XCTest
@testable import day04Swift

final class utilitiesTests: XCTestCase {

    func testMySplit() {
        let t0 = "hello world".split(separator: "xx")
        XCTAssertEqual(1, t0.count)
        XCTAssertEqual("hello world", t0[0])
        
        let t1 = "helloxxworld".split(separator: "xx")
        XCTAssertEqual(2, t1.count)
        XCTAssertEqual("hello", t1[0])
        XCTAssertEqual("world", t1[1])

        let t2 = "helloxx".split(separator: "xx")
        XCTAssertEqual(2, t2.count)
        XCTAssertEqual("hello", t2[0])
        XCTAssertEqual("", t2[1])
        
        let t3 = "xxhello".split(separator: "xx")
        XCTAssertEqual(2, t3.count)
        XCTAssertEqual("", t3[0])
        XCTAssertEqual("hello", t3[1])

        let t4 = "xx".split(separator: "xx")
        XCTAssertEqual(2, t4.count)
        XCTAssertEqual("", t4[0])
        XCTAssertEqual("", t4[1])

        let t5 = "xxxx".split(separator: "xx")
        XCTAssertEqual(3, t5.count)
        XCTAssertEqual("", t5[0])
        XCTAssertEqual("", t5[1])
        XCTAssertEqual("", t5[2])
    }
    
    
}
