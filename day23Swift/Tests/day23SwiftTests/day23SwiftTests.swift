import XCTest
@testable import day23Swift

final class day23SwiftTests: XCTestCase {
    func testPartTwo() {
        let testData  = Array([3,2,6,5,1,9,4,7,8] + Array((10...1000000)))
        let _  = process(testData,10000000)
        let firstCup = cupsForLabels[1]!.next
        let secondCup = cupsForLabels[1]!.next!.next
        let answer = (firstCup?.label ?? 0) * (secondCup?.label ?? 0)
        XCTAssertEqual(44541319250, answer)
    
    }

}
