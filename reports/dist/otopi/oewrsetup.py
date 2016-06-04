"""ovirt engine webadmin reports setup."""

import os

from otopi import plugin, util

from ovirt_engine_setup import constants as osetupcons
from ovirt_engine_setup.engine import constants as oenginecons


@util.export
class Plugin(plugin.PluginBase):
    """ovirt engine webadmin reports setup."""

    @plugin.event(
        stage=plugin.Stages.STAGE_CLOSEUP,
        after=(
            osetupcons.Stages.DIALOG_TITLES_E_SUMMARY,
        ),
    )
    def enable_ovirt_engine_webadmin_reports_plugin(self):
        os.system("ovirt-engine-webadmin-reports-setup --password=%s"
            % self.environment[oenginecons.ConfigEnv.ADMIN_PASSWORD])
        self.dialog.note(text="ovirt engine webadmin reports enabled.")
