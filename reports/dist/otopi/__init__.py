"""vm backup scheduler plugin."""


from otopi import util


from . import oewrsetup


@util.export
def createPlugins(context):
    oewrsetup.Plugin(context=context)


# vim: expandtab tabstop=4 shiftwidth=4
