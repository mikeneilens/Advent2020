import XCTest

#if !canImport(ObjectiveC)
public func allTests() -> [XCTestCaseEntry] {
    return [
        testCase(day04SwiftTests.allTests),
    ]
}
#endif
