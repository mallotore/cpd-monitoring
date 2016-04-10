package com.mallotore.monitoring

import com.mallotore.monitoring.model.ServerProbeRequest

class ServerProbeJob {

	def serverProbeJobService

    static triggers = {}

    def execute(context) {
    	def request = new ServerProbeRequest(ip: context.mergedJobDataMap.serverIp,
    										 port: context.mergedJobDataMap.serverPort,
    										 connectivityAlert: context.mergedJobDataMap.serverConnectivityAlert,
    										 diskPercentageAlert: context.mergedJobDataMap.serverDiskPercentageAlert)
        serverProbeJobService.probe(request)
    }
}
