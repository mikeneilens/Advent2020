import XCTest
@testable import day05Swift

final class day05SwiftTests: XCTestCase {
    

    func testBinaryToInteger() {
        XCTAssertEqual( 0, "0".binaryToInteger("1"))
        XCTAssertEqual( 1, "1".binaryToInteger("1"))
        XCTAssertEqual( 2, "10".binaryToInteger("1"))
        XCTAssertEqual( 3, "11".binaryToInteger("1"))
        XCTAssertEqual( 4, "100".binaryToInteger("1"))
    }
    func testBinaryToIntegerWithDifferentKindsOfDigits() {
        XCTAssertEqual( 0, "0".binaryToInteger("BR"))
        XCTAssertEqual( 1, "B".binaryToInteger("BR"))
        XCTAssertEqual( 2, "R0".binaryToInteger("BR"))
        XCTAssertEqual( 3, "RB".binaryToInteger("BR"))
        XCTAssertEqual( 4, "B00".binaryToInteger("BR"))
    }
    func testFindingTheMaxSeatIdOfBoardingPasses() {
        let sampleData = """
        BFFFBBFRRR
        BBFFBBFRLL
        FFFBBBFRRR
        """
        XCTAssertEqual(820,sampleData.maxSeat())
    }
    func testPartOne() {
        XCTAssertEqual(892, day05Data.maxSeat())
    }
    
    func testPartTwo() {
        XCTAssertEqual(625, day05Data.missingSeat())
    }
}
