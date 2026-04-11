---
layout: page
title: Arrick Git's Project Portfolio Page
---

### Project: RecruiterPlus

RecruiterPlus is a desktop application for recruiters to manage candidate information using CLI commands, with JavaFX-based GUI support. It is implemented in Java and follows an architecture with `Logic`, `Model`, `Storage`, and `UI` components.

Given below are my contributions to the project.

### Overview

My work focused on improving command usability and data quality constraints, while keeping documentation and UI behavior aligned with the implementation.

### Summary of Contributions

* **Code contributed**: [RepoSense link for my contributions](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=arrickgit&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

* **Enhancements implemented**:
  * Migrated command syntax from short prefixes (e.g. `n/`) to explicit long-form prefixes (e.g. `-name`) for the `add` workflow, and aligned parsing/usage behavior.
  * Implemented and refined input constraints for `add` parameters (name/phone/email/address/tag), including stricter phone validation and clearer validation behavior.
  * Implemented the `unmark` feature to allow reverting interviewed status for candidates.
  * Improved command error messaging, including clearer validation/constraint feedback for invalid inputs and duplicate-related scenarios.
  * Updated Help popup behavior/content to better reflect current command formats and usage.

* **Contributions to the User Guide**:
  * Updated command format documentation to match the long-form prefix convention.
  * Added and refined parameter constraint documentation for `add` and related commands.
  * Updated examples and command descriptions to reflect actual behavior after feature and validation changes.

* **Contributions to the Developer Guide**:
  * Updated implementation-related descriptions to stay consistent with command behavior and validation logic changes.
  * Improved use case and command behavior documentation based on testing feedback and bug reports.

* **Contributions to team-based tasks**:
  * Worked on integrating and validating feature changes with existing test suites.
  * Helped maintain alignment between code behavior, documentation, and acceptance criteria from PE findings.

* **Review and mentoring contributions**:
  * Reviewed team changes and provided implementation feedback on command handling and documentation consistency.
  * Assisted teammates in reproducing and resolving behavior mismatches between code and documentation.

* **Contributions beyond the project team**:
  * Reported and acted on PE findings by converting external tester feedback into concrete fixes and documentation updates.


