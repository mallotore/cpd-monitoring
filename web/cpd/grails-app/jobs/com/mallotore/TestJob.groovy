package com.mallotore

class TestJob {

	def serverProbeService

    static triggers = {}

    def execute() {
        serverProbeService.probe()
    }
}
